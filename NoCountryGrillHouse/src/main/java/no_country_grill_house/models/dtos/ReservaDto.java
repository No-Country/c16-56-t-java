package no_country_grill_house.models.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.Mesa;
import no_country_grill_house.models.enums.EstadoReserva;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDto {
    private long id;
    private Cliente cliente;
    private EstadoReserva estadoReserva;
    private LocalDateTime fechaWeb;
    private Boolean alta;
    private Mesa mesa;
}
