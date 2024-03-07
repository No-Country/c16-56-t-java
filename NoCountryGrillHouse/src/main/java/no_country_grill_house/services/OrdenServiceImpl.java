package no_country_grill_house.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.OrdenMapper;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.DetalleOrden;
import no_country_grill_house.models.Mesero;
import no_country_grill_house.models.Orden;
import no_country_grill_house.models.dtos.DetalleDto;
import no_country_grill_house.models.dtos.OrdenConDetalleDto;
import no_country_grill_house.models.dtos.OrdenDto;
import no_country_grill_house.models.enums.EstadoOrden;
import no_country_grill_house.repositories.DetalleOrdenRepository;
import no_country_grill_house.repositories.OrdenRepository;

@Service

public class OrdenServiceImpl implements OrdenService {
    @Autowired
    private OrdenRepository repository;

    @Autowired
    private OrdenMapper ordenMapper;

    @Autowired
    private DetalleOrdenRepository detalleOrdenRepository;

    @Transactional
    @Override
    public OrdenDto create(OrdenDto ordenDto) {
        ordenDto.setAlta(true);
        ordenDto.setEstadoOrden(EstadoOrden.INICIADO);
        ordenDto.setFechaAlta(LocalDateTime.now());
        Orden orden = repository.save(ordenMapper.toOrden(ordenDto));
        return ordenMapper.tOrdenDto(orden);
    }

    @Override
    public OrdenDto findById(Long id) {
        Orden orden = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe una orden con ID: " + id);
        });
        return ordenMapper.tOrdenDto(orden);
    }

    @Override
    public List<OrdenDto> findAll() {
        return repository.findAll()
                .stream()
                .map(ordenMapper::tOrdenDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrdenConDetalleDto> obtenerOrdenesConDetallesPorCliente(Cliente cliente) {
        List<Orden> ordenes = repository.findByCliente(cliente);
        List<OrdenConDetalleDto> ordenesConDetalles = new ArrayList<>();

        for (Orden orden : ordenes) {
            List<DetalleOrden> detalles = detalleOrdenRepository.findByOrden(orden);
            List<DetalleDto> detallesDto = new ArrayList<>();

            for (DetalleOrden detalle : detalles) {
                DetalleDto detalleDto = new DetalleDto();
                detalleDto.setCantidad(detalle.getCantidad());
                detalleDto.setPlatillo(detalle.getPlatillo());

                detallesDto.add(detalleDto);
            }

            OrdenConDetalleDto ordenConDetalleDto = new OrdenConDetalleDto();
            ordenConDetalleDto.setNumeroOrden(orden.getNumeroOrden());
            ordenConDetalleDto.setMesa(orden.getMesa());
            ordenConDetalleDto.setEstadoOrden(orden.getEstadoOrden());
            ordenConDetalleDto.setCliente(orden.getCliente());
            ordenConDetalleDto.setDetalles(detallesDto);
            ordenConDetalleDto.setFechaAlta(orden.getFechaAlta());

            ordenesConDetalles.add(ordenConDetalleDto);
        }

        return ordenesConDetalles;
    }

    @Override
    public List<OrdenConDetalleDto> obtenerOrdenesConDetallesPorMesero(Mesero mesero) {
        List<Orden> ordenes = repository.findByMesero(mesero.getId());
        List<OrdenConDetalleDto> ordenesConDetalles = new ArrayList<>();

        for (Orden orden : ordenes) {
            List<DetalleOrden> detalles = detalleOrdenRepository.findByOrden(orden);
            List<DetalleDto> detallesDto = new ArrayList<>();

            for (DetalleOrden detalle : detalles) {
                DetalleDto detalleDto = new DetalleDto();
                detalleDto.setCantidad(detalle.getCantidad());
                detalleDto.setPlatillo(detalle.getPlatillo());

                detallesDto.add(detalleDto);
            }

            OrdenConDetalleDto ordenConDetalleDto = new OrdenConDetalleDto();
            ordenConDetalleDto.setNumeroOrden(orden.getNumeroOrden());
            ordenConDetalleDto.setMesa(orden.getMesa());
            ordenConDetalleDto.setEstadoOrden(orden.getEstadoOrden());
            ordenConDetalleDto.setCliente(orden.getCliente());
            ordenConDetalleDto.setDetalles(detallesDto);
            ordenConDetalleDto.setFechaAlta(orden.getFechaAlta());

            ordenesConDetalles.add(ordenConDetalleDto);
        }

        return ordenesConDetalles;
    }

    @Override
    public List<OrdenConDetalleDto> obtenerOrdenesConDetalles() {
        List<Orden> ordenes = repository.findAll();
        List<OrdenConDetalleDto> ordenesConDetalles = new ArrayList<>();

        for (Orden orden : ordenes) {
            List<DetalleOrden> detalles = detalleOrdenRepository.findByOrden(orden);
            List<DetalleDto> detallesDto = new ArrayList<>();

            for (DetalleOrden detalle : detalles) {
                DetalleDto detalleDto = new DetalleDto();
                detalleDto.setCantidad(detalle.getCantidad());
                detalleDto.setPlatillo(detalle.getPlatillo());

                detallesDto.add(detalleDto);
            }

            OrdenConDetalleDto ordenConDetalleDto = new OrdenConDetalleDto();
            ordenConDetalleDto.setNumeroOrden(orden.getNumeroOrden());
            ordenConDetalleDto.setMesa(orden.getMesa());
            ordenConDetalleDto.setEstadoOrden(orden.getEstadoOrden());
            ordenConDetalleDto.setCliente(orden.getCliente());
            ordenConDetalleDto.setDetalles(detallesDto);
            ordenConDetalleDto.setFechaAlta(orden.getFechaAlta());

            ordenesConDetalles.add(ordenConDetalleDto);
        }

        return ordenesConDetalles;
    }
}
