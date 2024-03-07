package no_country_grill_house.models.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.Mesa;
import no_country_grill_house.models.enums.EstadoOrden;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrdenConDetalleDto {
    // Entidad Orden
    private Long numeroOrden;
    private Mesa mesa;
    private EstadoOrden estadoOrden;
    private LocalDateTime fechaAlta;
    private Cliente cliente;
    // Entidad DettalleOrden
    private List<DetalleDto> detalles;

}
