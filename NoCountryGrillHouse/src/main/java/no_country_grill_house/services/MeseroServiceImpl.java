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
import no_country_grill_house.mappers.FotoUsuarioMapper;
import no_country_grill_house.mappers.MeseroMapper;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.Mesero;
import no_country_grill_house.models.dtos.FotoUsuarioDto;
import no_country_grill_house.models.dtos.MeseroDto;
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

        repository.save(mesero);
        return meseroMapper.toMeseroDto(mesero);
    }

    public void guardarFotoPerfil(Long id, FotoUsuarioDto fotoUsuarioDto) {
        Mesero mesero = meseroMapper.toMesero(findById(id));
        mesero.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioDto));
        repository.save(mesero);
    }

}
