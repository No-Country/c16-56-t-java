package no_country_grill_house.models.dtos;

import com.google.cloud.Date;
import lombok.*;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.Reserva;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class OrdenDto {

    private Long numeroOrden;    
    //private Mesa mesa;    
    private Cliente cliente;  
    private Reserva reserva;
    private Date fecha;
    private boolean status;
}
