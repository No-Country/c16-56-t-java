package no_country_grill_house.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no_country_grill_house.models.Categoria;
import no_country_grill_house.models.Platillo;

@Repository
public interface PlatilloRepository extends JpaRepository<Platillo, Long> {

    List<Platillo> findByCategoria(Categoria categoria);

}