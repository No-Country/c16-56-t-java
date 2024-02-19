package no_country_grill_house.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

    private Cloudinary cloudinary;

    private Map<String, String> valores = new HashMap<>();

    // Configuracion Cloudinary
    public CloudinaryService() {
        valores.put("cloud_name", "dte154gnk");
        valores.put("api_key", "146417589721878");
        valores.put("api_secret", "EkVGVIZIYgosXmm8QHFbR2x_jgE");
        cloudinary = new Cloudinary(valores);
    }

    @SuppressWarnings("rawtypes")
    public Map subirFoto(MultipartFile multipartFile) throws IOException {
        File archivo = convetir(multipartFile);
        Map resultado = cloudinary.uploader().upload(archivo, ObjectUtils.emptyMap());
        archivo.delete();
        return resultado;
    }

    @SuppressWarnings("rawtypes")
    public Map borrar(String id) throws IOException {
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
    }

    public File convetir(MultipartFile multipartFile) throws IOException {
        File archivo = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream file = new FileOutputStream(archivo);
        file.write(multipartFile.getBytes());
        file.close();
        return archivo;
    }
}
