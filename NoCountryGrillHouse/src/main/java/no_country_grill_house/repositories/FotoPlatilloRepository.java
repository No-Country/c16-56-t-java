package no_country_grill_house.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no_country_grill_house.models.FotoPlatillo;

@Repository
public interface FotoPlatilloRepository extends JpaRepository<FotoPlatillo, Long> {

}
