package no_country_grill_house.models.dtos;

<<<<<<< HEAD
import java.util.Date;
import lombok.*;
=======
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
>>>>>>> 2b1acdc0e17baae69ecf9bfa390ad9562dc0cb2b
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
