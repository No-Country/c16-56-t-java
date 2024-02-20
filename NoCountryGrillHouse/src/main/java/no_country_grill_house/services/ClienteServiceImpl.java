package no_country_grill_house.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.ClienteMapper;
import no_country_grill_house.mappers.DireccionMapper;
import no_country_grill_house.mappers.FotoUsuarioMapper;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.dtos.ClienteDireccionDto;
import no_country_grill_house.models.dtos.ClienteDto;
import no_country_grill_house.models.dtos.DireccionDto;
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
    private DireccionService direccionService;

    @Autowired
    private DireccionMapper direccionMapper;

    @Autowired
    private FotoUsuarioMapper fotoUsuarioMapper;

    @Transactional
    @Override
    public ClienteDto create(ClienteDireccionDto clienteDireccionDto) {
        Cliente cliente = new Cliente();

        if (clienteDireccionDto.getDireccionDto().getCalle() == null && clienteDireccionDto.getDireccionDto()
                .getNumero() == null && clienteDireccionDto.getDireccionDto().getCiudad() == null) {
            cliente.setDireccion(null);
        } else {
            // Verificar las propiedades de la dirección solo si no es nula
            DireccionDto direccionDto = clienteDireccionDto.getDireccionDto();
            if (direccionDto.getCalle() == null) {
                throw new GrillHouseException("Debe ingresar una calle");
            }
            if (direccionDto.getNumero() == null) {
                throw new GrillHouseException("Debe ingresar un número");
            }
            if (direccionDto.getCiudad() == null) {
                throw new GrillHouseException("Debe ingresar una ciudad");
            }

            // Si la dirección no es nula, crear y guardar la dirección
            DireccionDto direccionDtoGuardada = direccionService.create(direccionDto);
            cliente.setDireccion(direccionMapper.toDireccion(direccionDtoGuardada));
        }

        validar(clienteDireccionDto.getClienteDto());

        cliente.setNombre(clienteDireccionDto.getClienteDto().getNombre());
        cliente.setEmail(clienteDireccionDto.getClienteDto().getEmail());
        cliente.setPassword(clienteDireccionDto.getClienteDto().getPassword());
        cliente.setTelefono(clienteDireccionDto.getClienteDto().getTelefono());
        cliente.setAlta(true);
        cliente.setFechaAlta(LocalDateTime.now());
        cliente.setRol(Rol.CLIENTE);

        repository.save(cliente);
        return clienteMapper.toClienteDto(cliente);

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

    private void validar(ClienteDto clienteDto) {

        if (clienteDto.getNombre() == null || clienteDto.getNombre().trim() == "")
            throw new GrillHouseException("Debe ingresar un nombre válido y sin espacios");
        if (clienteDto.getEmail() == null || clienteDto.getEmail().trim() == "")
            throw new GrillHouseException("Debe ingresar un email válido y sin espacios");
        if (clienteDto.getPassword() == null || clienteDto.getPassword().trim() == "")
            throw new GrillHouseException("Debe ingresar un password válido y sin espacios");
        if (clienteDto.getTelefono() == null || clienteDto.getTelefono().trim() == "")
            throw new GrillHouseException("Debe ingresar un teléfono válido y sin espacios");

    }
}
