package no_country_grill_house.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.Orden;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    List<Orden> findByCliente(Cliente cliente);

    @Query("SELECT o FROM Orden o WHERE o.mesa.mesero.id = :id")
    List<Orden> findByMesero(@Param("id") Long id);

}
