package no_country_grill_house.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.config.JwtService;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.DireccionMapper;
import no_country_grill_house.mappers.FotoUsuarioMapper;
import no_country_grill_house.mappers.MeseroMapper;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.Mesero;
import no_country_grill_house.models.dtos.FotoUsuarioDto;
import no_country_grill_house.models.dtos.MeseroDto;
import no_country_grill_house.models.dtos.PasswordDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.repositories.MeseroRepository;

@Service
public class MeseroServiceImpl implements MeseroService {

    @Autowired
    private MeseroRepository repository;

    @Autowired
    private MeseroMapper meseroMapper;

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
    public AuthResponse create(MeseroDto meseroDto) {
        Mesero mesero = new Mesero();

        mesero.setNombre(meseroDto.getNombre());
        mesero.setEmail(meseroDto.getEmail());
        mesero.setPassword(passwordEncoder.encode(meseroDto.getPassword()));
        mesero.setTelefono(meseroDto.getTelefono());
        mesero.setAlta(true);
        mesero.setFechaAlta(LocalDateTime.now());
        mesero.setRol(Rol.MESERO);

        repository.save(mesero);

        var jwtToken = jwtService.generateToken(mesero);

        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public MeseroDto findById(Long id) {
        Mesero mesero = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el mesero con el id: " + id);
        });
        return meseroMapper.toMeseroDto(mesero);
    }

    @Override
    public MeseroDto findByEmail(String email) {
        Mesero mesero = repository.findMeseroByEmail(email).orElseThrow(() -> {
            throw new GrillHouseException("No existe el mesero con el email: " + email);
        });
        return meseroMapper.toMeseroDto(mesero);
    }

    @Override
    public List<MeseroDto> findAll() {
        return repository.findAll()
                .stream()
                .map(meseroMapper::toMeseroDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el mesero con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void softDeleteById(Long id) {
        Mesero mesero = meseroMapper.toMesero(findById(id));
        mesero.setAlta(false);
        repository.save(mesero);
    }

    @Transactional
    @Override
    public void alta(Long id) {
        Mesero mesero = meseroMapper.toMesero(findById(id));
        mesero.setAlta(true);
        repository.save(mesero);
    }

    @Transactional
    @Override
    public MeseroDto update(Long id, MeseroDto meseroDto) {
        Mesero mesero = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el Mesero con el id: " + id);
        });

        if (meseroDto.getNombre() != null)
            mesero.setNombre(meseroDto.getNombre());
        if (meseroDto.getEmail() != null)
            mesero.setEmail(meseroDto.getEmail());
        if (meseroDto.getPassword() != null)
            mesero.setPassword(meseroDto.getPassword());
        if (meseroDto.getTelefono() != null)
            mesero.setTelefono(meseroDto.getTelefono());
        if (meseroDto.getFoto() != null) {
            mesero.setFoto(meseroDto.getFoto());
        }
        if (mesero.getDireccion() == null) {
            if (meseroDto.getDireccion().getCalle() != null && meseroDto.getDireccion().getNumero() != null
                    && meseroDto.getDireccion().getCiudad() != null) {
                mesero.setDireccion(direccionMapper.toDireccion(
                        direccionServiceImpl.create(direccionMapper.toDireccionDto(meseroDto.getDireccion()))));
            }
        } else {
            direccionServiceImpl.update(mesero.getDireccion().getId(),
                    direccionMapper.toDireccionDto(meseroDto.getDireccion()));
        }

        repository.save(mesero);
        return meseroMapper.toMeseroDto(mesero);
    }

    @Override
    public void modificarPassword(PasswordDto passwordDto) {
        Mesero mesero = meseroMapper.toMesero(findByEmail(passwordDto.getEmail()));

        if (!passwordEncoder.matches(passwordDto.getPasswordActual(), mesero.getPassword())) {
            throw new GrillHouseException("La contraseña actual no coincide con la ingresada");
        }

        if (!passwordDto.getPassword1().equals(passwordDto.getPassword2())) {
            throw new GrillHouseException("Las contraseñas nuevas no coinciden");
        }
        mesero.setPassword(passwordEncoder.encode(passwordDto.getPassword1()));
        repository.save(mesero);

    }

    public void guardarFotoPerfil(Long id, FotoUsuarioDto fotoUsuarioDto) {
        Mesero mesero = meseroMapper.toMesero(findById(id));
        mesero.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioDto));
        repository.save(mesero);
    }

}
