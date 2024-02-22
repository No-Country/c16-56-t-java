package no_country_grill_house.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import no_country_grill_house.models.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long > {

}
