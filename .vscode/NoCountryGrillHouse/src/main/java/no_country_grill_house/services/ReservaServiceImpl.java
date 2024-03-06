package no_country_grill_house.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.ReservaMapper;
import no_country_grill_house.models.Reserva;
import no_country_grill_house.models.dtos.ReservaDto;
import no_country_grill_house.models.enums.EstadoReserva;
import no_country_grill_house.repositories.ReservaRepository;

@Service
public class ReservaServiceImpl implements ReservaService {
    @Autowired
    private ReservaRepository repository;
    @Autowired
    private ReservaMapper reservaMapper;

    @Transactional
    @Override
    public ReservaDto create(ReservaDto reservaDto) {
        reservaDto.setAlta(true);
        Reserva reserva = repository.save(reservaMapper.toReserva(reservaDto));
        return reservaMapper.toReservaDto(reserva);
    }

    @Override
    public ReservaDto findById(long id) {
        Reserva reserva = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe reserva con ID: " + id);
        });
        return reservaMapper.toReservaDto(reserva);
    }

    @Override
    public List<ReservaDto> findAll() {
        return repository.findAll()
                .stream()
                .map(reservaMapper::toReservaDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ReservaDto updateStatus(Long id, String status) {
        if (EstadoReserva.valueOf(status) == null) {
            throw new GrillHouseException("No existe un Estado de Reserva " + status);
        }

        Reserva reserva = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe reserva con ID: " + id);
        });

        if (reserva.getEstadoReserva().toString() != status) {
            reserva.setEstadoReserva(EstadoReserva.valueOf(status));
        }
        repository.save(reserva);
        return reservaMapper.toReservaDto(reserva);
    }

    @Transactional
    @Override
    public ReservaDto update(Long id, ReservaDto reservaDto) {
        Reserva reserva = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe la reserva con el id: " + id);
        });

        if (reservaDto.getFechaHora() != null)
            reserva.setFechaHora(reservaDto.getFechaHora());

        repository.save(reserva);
        return reservaMapper.toReservaDto(reserva);
    }
}
