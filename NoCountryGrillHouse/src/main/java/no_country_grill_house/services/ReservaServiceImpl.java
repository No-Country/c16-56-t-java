package no_country_grill_house.services;

import jakarta.transaction.Transactional;
import no_country_grill_house.exceptions.GrillHouseException;
import no_country_grill_house.mappers.ReservaMapper;
import no_country_grill_house.models.Reserva;
import no_country_grill_house.models.dtos.ReservaDto;
import no_country_grill_house.models.enums.Estado_Reserva;
import no_country_grill_house.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaServiceImpl implements ReservaService {
    @Autowired
    private ReservaRepository repository;
    @Autowired
    private ReservaMapper reservaMapper;

    @Transactional
    @Override
    public ReservaDto create(ReservaDto reservaDto) {
        Reserva reserva = repository.save(reservaMapper.toReserva(reservaDto));
        return reservaMapper.toReservaDto(reserva);
    }

    @Override
    public Reserva findById(long id) {
        Reserva reserva = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe reserva con ID: " + id);
        });
        return reserva;
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
    public ReservaDto updateStatus(Long id, ReservaDto reservaDto) {
        Reserva reserva = repository.findById(id).orElseThrow(() -> {
            throw new GrillHouseException("No existe reserva con ID: " + id);
        });

        if (reserva.getEstado_reserva() != reservaDto.getEstado_reserva()) {
            reserva.setEstado_reserva(reservaDto.getEstado_reserva());
        }
        repository.save(reserva);
        return reservaMapper.toReservaDto(reserva);
    }
}
