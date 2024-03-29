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
import no_country_grill_house.mappers.JefeCocinaMapper;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.JefeCocina;
import no_country_grill_house.models.dtos.FotoUsuarioDto;
import no_country_grill_house.models.dtos.JefeCocinaDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.repositories.JefeCocinaRepository;

@Service
public class JefeCocinaServiceImpl implements JefeCocinaService {

    @Autowired
    private JefeCocinaRepository repository;

    @Autowired
    private JefeCocinaMapper jefeCocinaMapper;

    @Autowired
    private FotoUsuarioMapper fotoUsuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    @Override
    public AuthResponse create(JefeCocinaDto jefeCocinaDto) {
        JefeCocina jefeCocina = new JefeCocina();

        jefeCocina.setNombre(jefeCocinaDto.getNombre());
        jefeCocina.setEmail(jefeCocinaDto.getEmail());
        jefeCocina.setPassword(passwordEncoder.encode(jefeCocinaDto.getPassword()));
        jefeCocina.setTelefono(jefeCocinaDto.getTelefono());
        jefeCocina.setAlta(true);
        jefeCocina.setFechaAlta(LocalDateTime.now());
        jefeCocina.setRol(Rol.JEFE_COCINA);

        repository.save(jefeCocina);

        var jwtToken = jwtService.generateToken(jefeCocina);

        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public JefeCocinaDto findById(Long id) {
        JefeCocina jefeCocina = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el jefe de cocina con el id: " + id);
        });
        return jefeCocinaMapper.toJefeCocinaDto(jefeCocina);
    }

    @Override
    public List<JefeCocinaDto> findAll() {
        return repository.findAll()
                .stream()
                .map(jefeCocinaMapper::toJefeCocinaDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el jefeCocina con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void softDeleteById(Long id) {
        JefeCocina jefeCocina = jefeCocinaMapper.toJefeCocina(findById(id));
        jefeCocina.setAlta(false);
        repository.save(jefeCocina);
    }

    @Transactional
    @Override
    public void alta(Long id) {
        JefeCocina jefeCocina = jefeCocinaMapper.toJefeCocina(findById(id));
        jefeCocina.setAlta(true);
        repository.save(jefeCocina);
    }

    @Transactional
    @Override
    public JefeCocinaDto update(Long id, JefeCocinaDto jefeCocinaDto) {
        JefeCocina jefeCocina = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el JefeCocina con el id: " + id);
        });

        if (jefeCocinaDto.getNombre() != null)
            jefeCocina.setNombre(jefeCocinaDto.getNombre());
        if (jefeCocinaDto.getEmail() != null)
            jefeCocina.setEmail(jefeCocinaDto.getEmail());
        if (jefeCocinaDto.getPassword() != null)
            jefeCocina.setPassword(jefeCocinaDto.getPassword());
        if (jefeCocinaDto.getTelefono() != null)
            jefeCocina.setTelefono(jefeCocinaDto.getTelefono());

        repository.save(jefeCocina);
        return jefeCocinaMapper.toJefeCocinaDto(jefeCocina);
    }

    public void guardarFotoPerfil(Long id, FotoUsuarioDto fotoUsuarioDto) {
        JefeCocina jefeCocina = jefeCocinaMapper.toJefeCocina(findById(id));
        jefeCocina.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioDto));
        repository.save(jefeCocina);
    }

}
