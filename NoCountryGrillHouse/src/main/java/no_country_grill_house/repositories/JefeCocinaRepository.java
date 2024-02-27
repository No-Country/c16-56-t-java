package no_country_grill_house.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no_country_grill_house.models.JefeCocina;

@Repository
public interface JefeCocinaRepository extends JpaRepository<JefeCocina, Long> {

    Optional<JefeCocina> findJefeCocinaByEmail(String email);

}
