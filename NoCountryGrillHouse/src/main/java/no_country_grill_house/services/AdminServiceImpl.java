package no_country_grill_house.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.config.JwtService;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.AdminMapper;
import no_country_grill_house.mappers.DireccionMapper;
import no_country_grill_house.mappers.FotoUsuarioMapper;
import no_country_grill_house.models.Admin;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.dtos.AdminDto;
import no_country_grill_house.models.dtos.FotoUsuarioDto;
import no_country_grill_house.models.dtos.PasswordDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.repositories.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository repository;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private FotoUsuarioMapper fotoUsuarioMapper;

    @Autowired
    private DireccionServiceImpl direccionServiceImpl;

    @Autowired
    private DireccionMapper direccionMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    @Override
    public AuthResponse create(AdminDto adminDto) {
        Admin admin = new Admin();

        admin.setNombre(adminDto.getNombre());
        admin.setEmail(adminDto.getEmail());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        admin.setTelefono(adminDto.getTelefono());
        admin.setAlta(true);
        admin.setFechaAlta(LocalDateTime.now());
        admin.setRol(Rol.ADMIN);

        repository.save(admin);

        var jwtToken = jwtService.generateToken(admin);

        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public AdminDto findById(Long id) {
        Admin admin = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el admin con el id: " + id);
        });
        return adminMapper.toAdminDto(admin);
    }

    @Override
    public AdminDto findByEmail(String email) {
        Admin admin = repository.findAdminByEmail(email).orElseThrow(() -> {
            throw new GrillHouseException("No existe el admin con el email: " + email);
        });
        return adminMapper.toAdminDto(admin);
    }

    @Transactional
    @Override
    public AdminDto update(Long id, AdminDto adminDto) {
        Admin admin = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el Admin con el id: " + id);
        });

        if (adminDto.getNombre() != null)
            admin.setNombre(adminDto.getNombre());
        if (adminDto.getEmail() != null)
            admin.setEmail(adminDto.getEmail());
        if (adminDto.getPassword() != null)
            admin.setPassword(adminDto.getPassword());
        if (adminDto.getTelefono() != null)
            admin.setTelefono(adminDto.getTelefono());
        if (adminDto.getFoto() != null) {
            admin.setFoto(adminDto.getFoto());
        }
        if (admin.getDireccion() == null) {
            if (adminDto.getDireccion().getCalle() != null && adminDto.getDireccion().getNumero() != null
                    && adminDto.getDireccion().getCiudad() != null) {
                admin.setDireccion(direccionMapper.toDireccion(
                        direccionServiceImpl.create(direccionMapper.toDireccionDto(adminDto.getDireccion()))));
            }
        } else {
            direccionServiceImpl.update(admin.getDireccion().getId(),
                    direccionMapper.toDireccionDto(adminDto.getDireccion()));
        }

        repository.save(admin);
        return adminMapper.toAdminDto(admin);
    }

    @Override
    public void modificarPassword(PasswordDto passwordDto) {
        Admin admin = adminMapper.toAdmin(findByEmail(passwordDto.getEmail()));

        if (!passwordEncoder.matches(passwordDto.getPasswordActual(), admin.getPassword())) {
            throw new GrillHouseException("La contraseña actual no coincide con la ingresada");
        }

        if (!passwordDto.getPassword1().equals(passwordDto.getPassword2())) {
            throw new GrillHouseException("Las contraseñas nuevas no coinciden");
        }
        admin.setPassword(passwordEncoder.encode(passwordDto.getPassword1()));
        repository.save(admin);

    }

    public void guardarFotoPerfil(Long id, FotoUsuarioDto fotoUsuarioDto) {
        Admin admin = adminMapper.toAdmin(findById(id));
        admin.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioDto));
        repository.save(admin);
    }

}
