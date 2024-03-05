package no_country_grill_house.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import no_country_grill_house.mappers.FotoPlatilloMapper;
import no_country_grill_house.mappers.FotoUsuarioMapper;
import no_country_grill_house.models.dtos.AdminDto;
import no_country_grill_house.models.dtos.ClienteDto;
import no_country_grill_house.models.dtos.FotoPlatilloDto;
import no_country_grill_house.models.dtos.FotoUsuarioDto;
import no_country_grill_house.models.dtos.JefeCocinaDto;
import no_country_grill_house.models.dtos.MeseroDto;
import no_country_grill_house.models.dtos.PlatilloDto;
import no_country_grill_house.models.enums.Rol;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Service
public class S3ServiceImpl implements S3Service {

    String downloadsPath = System.getProperty("user.home") + "/Downloads/";

    private final S3Client s3Client;

    @Autowired
    private FotoUsuarioServiceImpl fotoUsuarioServiceImpl;

    @Autowired
    private FotoUsuarioMapper fotoUsuarioMapper;

    @Autowired
    private FotoPlatilloServiceImpl fotoPlatilloServiceImpl;

    @Autowired
    private FotoPlatilloMapper fotoPlatilloMapper;

    @Autowired
    private ClienteServiceImpl clienteServiceImpl;

    @Autowired
    private AdminServiceImpl adminServiceImpl;

    @Autowired
    private JefeCocinaServiceImpl jefeCocinaServiceImpl;

    @Autowired
    private MeseroServiceImpl meseroServiceImpl;

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String uploadFileUsuario(MultipartFile file, String email, Rol rol) throws IOException {
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("grillhousefotos")
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            FotoUsuarioDto fotoUsuarioDto = new FotoUsuarioDto();
            fotoUsuarioDto.setNombre(fileName);
            fotoUsuarioDto.setUrl("https://grillhousefotos.s3.sa-east-1.amazonaws.com/" + fileName);
            fotoUsuarioDto.setFotoId(UUID.randomUUID().toString());
            fotoUsuarioDto.setAlta(true);
            FotoUsuarioDto fotoUsuarioGuardada = fotoUsuarioServiceImpl.create(fotoUsuarioDto);

            if (rol.equals(Rol.CLIENTE)) {
                ClienteDto clienteDto = clienteServiceImpl.findByEmail(email);
                ClienteDto clienteDtoFoto = new ClienteDto();
                clienteDtoFoto.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioGuardada));
                clienteServiceImpl.update(clienteDto.getId(), clienteDtoFoto);
            } else if (rol.equals(Rol.ADMIN)) {
                AdminDto adminDto = adminServiceImpl.findByEmail(email);
                AdminDto adminDtoFoto = new AdminDto();
                adminDtoFoto.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioGuardada));
                adminServiceImpl.update(adminDto.getId(), adminDtoFoto);
            } else if (rol.equals(Rol.JEFE_COCINA)) {
                JefeCocinaDto jefeCocinaDto = jefeCocinaServiceImpl.findByEmail(email);
                JefeCocinaDto jefeCocinaDtoFoto = new JefeCocinaDto();
                jefeCocinaDtoFoto.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioGuardada));
                jefeCocinaServiceImpl.update(jefeCocinaDto.getId(), jefeCocinaDtoFoto);
            } else if (rol.equals(Rol.MESERO)) {
                MeseroDto meseroDto = meseroServiceImpl.findByEmail(email);
                MeseroDto meseroDtoFoto = new MeseroDto();
                meseroDtoFoto.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioGuardada));
                meseroServiceImpl.update(meseroDto.getId(), meseroDtoFoto);
            }

            return fotoUsuarioDto.getUrl();
        } catch (IOException e) {
            throw new IOException("Error al cargar la imagen: " + e.getMessage());
        }
    }

    @Override
    public String updateFileUsuario(MultipartFile file, String email, Rol rol) throws IOException {
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("grillhousefotos")
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            String fotoEliminar = "";
            FotoUsuarioDto fotoUsuarioDto = null;

            if (rol.equals(Rol.CLIENTE)) {
                ClienteDto clienteDto = clienteServiceImpl.findByEmail(email);
                fotoUsuarioDto = fotoUsuarioMapper.toFotoUsuarioDto(clienteDto.getFoto());
                fotoEliminar = fotoUsuarioDto.getNombre();
                fotoUsuarioDto.setNombre(fileName);
                fotoUsuarioDto.setUrl("https://grillhousefotos.s3.sa-east-1.amazonaws.com/" + fileName);
                fotoUsuarioDto.setFotoId(UUID.randomUUID().toString());
                fotoUsuarioServiceImpl.update(fotoUsuarioDto.getId(), fotoUsuarioDto);
            } else if (rol.equals(Rol.ADMIN)) {
                AdminDto adminDto = adminServiceImpl.findByEmail(email);
                fotoUsuarioDto = fotoUsuarioMapper.toFotoUsuarioDto(adminDto.getFoto());
                fotoEliminar = fotoUsuarioDto.getNombre();
                fotoUsuarioDto.setNombre(fileName);
                fotoUsuarioDto.setUrl("https://grillhousefotos.s3.sa-east-1.amazonaws.com/" + fileName);
                fotoUsuarioDto.setFotoId(UUID.randomUUID().toString());
                fotoUsuarioServiceImpl.update(fotoUsuarioDto.getId(), fotoUsuarioDto);
            } else if (rol.equals(Rol.JEFE_COCINA)) {
                JefeCocinaDto jefeCocinaDto = jefeCocinaServiceImpl.findByEmail(email);
                fotoUsuarioDto = fotoUsuarioMapper.toFotoUsuarioDto(jefeCocinaDto.getFoto());
                fotoEliminar = fotoUsuarioDto.getNombre();
                fotoUsuarioDto.setNombre(fileName);
                fotoUsuarioDto.setUrl("https://grillhousefotos.s3.sa-east-1.amazonaws.com/" + fileName);
                fotoUsuarioDto.setFotoId(UUID.randomUUID().toString());
                fotoUsuarioServiceImpl.update(fotoUsuarioDto.getId(), fotoUsuarioDto);
            } else if (rol.equals(Rol.MESERO)) {
                MeseroDto meseroDto = meseroServiceImpl.findByEmail(email);
                fotoUsuarioDto = fotoUsuarioMapper.toFotoUsuarioDto(meseroDto.getFoto());
                fotoEliminar = fotoUsuarioDto.getNombre();
                fotoUsuarioDto.setNombre(fileName);
                fotoUsuarioDto.setUrl("https://grillhousefotos.s3.sa-east-1.amazonaws.com/" + fileName);
                fotoUsuarioDto.setFotoId(UUID.randomUUID().toString());
                fotoUsuarioServiceImpl.update(fotoUsuarioDto.getId(), fotoUsuarioDto);
            }

            deleteFile(fotoEliminar);

            return fotoUsuarioDto.getUrl();
        } catch (IOException e) {
            throw new IOException("Error al actualizar la imagen: " + e.getMessage());
        }
    }

    @Override
    public FotoPlatilloDto uploadFilePlatillo(MultipartFile file) throws IOException {
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("grillhousefotos")
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            FotoPlatilloDto fotoPlatilloDto = new FotoPlatilloDto();
            fotoPlatilloDto.setNombre(fileName);
            fotoPlatilloDto.setUrl("https://grillhousefotos.s3.sa-east-1.amazonaws.com/" + fileName);
            fotoPlatilloDto.setFotoId(UUID.randomUUID().toString());
            fotoPlatilloDto.setAlta(true);
            FotoPlatilloDto fotoPlatilloGuardada = fotoPlatilloServiceImpl.create(fotoPlatilloDto);

            return fotoPlatilloGuardada;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String updateFilePlatillo(MultipartFile file, PlatilloDto platilloDto) throws IOException {
        try {
            String fileName = generateUniqueFileName(file.getOriginalFilename());
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("grillhousefotos")
                    .key(fileName)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

            String fotoEliminar = "";

            FotoPlatilloDto fotoPlatilloDto = fotoPlatilloMapper.toFotoPlatilloDto(platilloDto.getFoto());
            fotoEliminar = fotoPlatilloDto.getNombre();
            fotoPlatilloDto.setNombre(fileName);
            fotoPlatilloDto.setUrl("https://grillhousefotos.s3.sa-east-1.amazonaws.com/" + fileName);
            fotoPlatilloDto.setFotoId(UUID.randomUUID().toString());
            fotoPlatilloServiceImpl.update(fotoPlatilloDto.getId(), fotoPlatilloDto);

            deleteFile(fotoEliminar);

            return "Imagen actualizada correctamente";
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String deleteFile(String fileName) throws IOException {
        if (!existe(fileName)) {
            return "El archivo solicitado no existe";
        }
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket("grillhousefotos")
                    .key(fileName)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);

            return "Archivo borrado correctamente";
        } catch (S3Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String downloadFile(String fileName) throws IOException {
        if (!existe(fileName)) {
            return "El archivo solicitado no existe";
        }
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("grillhousefotos")
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> resultado = s3Client.getObject(request);
        try (FileOutputStream fos = new FileOutputStream(downloadsPath + fileName)) {
            byte[] read_buf = new byte[1024];
            int read_len = 0;

            while ((read_len = resultado.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
        return "Archivo descargado correctamente";
    }

    private boolean existe(String objectKey) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket("grillhousefotos")
                    .key(objectKey)
                    .build();
            s3Client.headObject(headObjectRequest);
            return true;
        } catch (S3Exception e) {
            if (e.statusCode() == 404) {
                return false;
            } else {
                return false;
            }
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

}
