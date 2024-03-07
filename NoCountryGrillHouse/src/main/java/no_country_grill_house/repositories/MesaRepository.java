package no_country_grill_house.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import no_country_grill_house.models.Mesa;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {

    @Query("SELECT m FROM Mesa m WHERE m.cantidadPersonas = :cantidadPersonas and m.alta = true")
    List<Mesa> findByCantidadPersonas(Integer cantidadPersonas);

}