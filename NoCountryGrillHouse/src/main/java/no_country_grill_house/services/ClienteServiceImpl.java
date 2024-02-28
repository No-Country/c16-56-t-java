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
import no_country_grill_house.mappers.ClienteMapper;
import no_country_grill_house.mappers.FotoUsuarioMapper;
import no_country_grill_house.models.AuthResponse;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.dtos.ClienteDto;
import no_country_grill_house.models.dtos.FotoUsuarioDto;
import no_country_grill_house.models.enums.Rol;
import no_country_grill_house.repositories.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private FotoUsuarioMapper fotoUsuarioMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    @Override
    public AuthResponse create(ClienteDto clienteDto) {
        Cliente cliente = new Cliente();

        cliente.setNombre(clienteDto.getNombre());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setPassword(passwordEncoder.encode(clienteDto.getPassword()));
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setAlta(true);
        cliente.setFechaAlta(LocalDateTime.now());
        cliente.setRol(Rol.CLIENTE);

        repository.save(cliente);

        var jwtToken = jwtService.generateToken(cliente);

        return AuthResponse.builder()
                .token(jwtToken).build();
    }

    @Override
    public ClienteDto findById(Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el cliente con el id: " + id);
        });
        return clienteMapper.toClienteDto(cliente);
    }

    @Override
    public List<ClienteDto> findAll() {
        return repository.findAll()
                .stream()
                .map(clienteMapper::toClienteDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el cliente con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void softDeleteById(Long id) {
        Cliente cliente = clienteMapper.toCliente(findById(id));
        cliente.setAlta(false);
        repository.save(cliente);
    }

    @Transactional
    @Override
    public void alta(Long id) {
        Cliente cliente = clienteMapper.toCliente(findById(id));
        cliente.setAlta(true);
        repository.save(cliente);
    }

    @Transactional
    @Override
    public ClienteDto update(Long id, ClienteDto clienteDto) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el Cliente con el id: " + id);
        });

        if (clienteDto.getNombre() != null)
            cliente.setNombre(clienteDto.getNombre());
        if (clienteDto.getEmail() != null)
            cliente.setEmail(clienteDto.getEmail());
        if (clienteDto.getPassword() != null)
            cliente.setPassword(clienteDto.getPassword());
        if (clienteDto.getTelefono() != null)
            cliente.setTelefono(clienteDto.getTelefono());

        repository.save(cliente);
        return clienteMapper.toClienteDto(cliente);
    }

    public void guardarFotoPerfil(Long id, FotoUsuarioDto fotoUsuarioDto) {
        Cliente cliente = clienteMapper.toCliente(findById(id));
        cliente.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioDto));
        repository.save(cliente);
    }

}
