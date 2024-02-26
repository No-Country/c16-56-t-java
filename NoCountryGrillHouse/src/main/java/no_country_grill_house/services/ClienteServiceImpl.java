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
    public Cliente findById(Long id) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el Cliente con el id: " + id);
        });
        return cliente;
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
            throw new GrillHouseException("No existe el Cliente con el id: " + id);
        });
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public ClienteDto update(Long id, ClienteDto clienteDto) {
        Cliente cliente = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe el Cliente con el id: " + id);
        });

        if (cliente.getNombre() != null)
            cliente.setNombre(clienteDto.getNombre());
        if (cliente.getEmail() != null)
            cliente.setEmail(clienteDto.getEmail());
        if (cliente.getPassword() != null)
            cliente.setPassword(clienteDto.getPassword());
        if (cliente.getTelefono() != null)
            cliente.setTelefono(clienteDto.getTelefono());
        // if (cliente.getDireccion() != null)
        // cliente.setDireccion(direccionMapper.toDireccion(clienteDto.getDireccionDto()));

        repository.save(cliente);
        return clienteMapper.toClienteDto(cliente);
    }

    public void guardarFotoPerfil(Long id, FotoUsuarioDto fotoUsuarioDto) {
        Cliente cliente = findById(id);
        cliente.setFoto(fotoUsuarioMapper.toFotoUsuario(fotoUsuarioDto));
        repository.save(cliente);
    }

    // private void validar(ClienteDto clienteDto) {

    // if (clienteDto.getNombre() == null || clienteDto.getNombre().trim() == "")
    // throw new GrillHouseException("Debe ingresar un nombre válido y sin
    // espacios");
    // if (clienteDto.getEmail() == null || clienteDto.getEmail().trim() == "")
    // throw new GrillHouseException("Debe ingresar un email válido y sin
    // espacios");
    // if (clienteDto.getPassword() == null || clienteDto.getPassword().trim() ==
    // "")
    // throw new GrillHouseException("Debe ingresar un password válido y sin
    // espacios");
    // if (clienteDto.getTelefono() == null || clienteDto.getTelefono().trim() ==
    // "")
    // throw new GrillHouseException("Debe ingresar un teléfono válido y sin
    // espacios");

    // }
}
