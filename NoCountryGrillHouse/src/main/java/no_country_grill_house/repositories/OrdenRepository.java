package no_country_grill_house.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.Mesero;
import no_country_grill_house.models.Orden;
import no_country_grill_house.models.enums.EstadoOrden;

public interface OrdenRepository extends JpaRepository<Orden, Long> {

    List<Orden> findByCliente(Cliente cliente);

    @Query("SELECT o FROM Orden o WHERE o.mesa.mesero = :mesero AND o.estadoOrden = :estadoOrden")
    List<Orden> findByEstadoYMesero(@Param("mesero") Mesero mesero,
            @Param("estadoOrden") EstadoOrden estadoOrden);

    @Query("SELECT o FROM Orden o WHERE o.estadoOrden = :estado")
    List<Orden> findByEstado(@Param("estado") EstadoOrden estado);

    @Modifying
    @Transactional
    @Query("UPDATE Orden o SET o.estadoOrden = :nuevoEstado WHERE o.numeroOrden = :numeroOrden")
    void actualizarEstadoOrden(@Param("numeroOrden") Long numeroOrden, @Param("nuevoEstado") EstadoOrden nuevoEstado);
}
