package no_country_grill_house.repositories;

import no_country_grill_house.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva,Long> {
}
