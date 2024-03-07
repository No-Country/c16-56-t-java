package no_country_grill_house.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no_country_grill_house.models.DetalleOrden;
import no_country_grill_house.models.Orden;

@Repository
public interface DetalleOrdenRepository extends JpaRepository<DetalleOrden, Long> {

    List<DetalleOrden> findByOrden(Orden orden);

}