package no_country_grill_house.models.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.Reserva;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrdenDto {

    private Long numeroOrden;
    // private Mesa mesa;
    private Cliente cliente;
    private Reserva reserva;
    private LocalDateTime fechaAlta;
    private Boolean alta;
    private boolean status;
}
