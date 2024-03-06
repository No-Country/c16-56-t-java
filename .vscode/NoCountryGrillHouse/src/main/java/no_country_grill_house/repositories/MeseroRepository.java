package no_country_grill_house.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no_country_grill_house.models.Mesero;

@Repository
public interface MeseroRepository extends JpaRepository<Mesero, Long> {

    Optional<Mesero> findMeseroByEmail(String email);

}