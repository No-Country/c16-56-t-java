package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.enums.Estado_Reserva;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDto {
    private long id;
    private Cliente cliente;
    private LocalTime fechaHora;
    private Estado_Reserva estado_reserva;
    private Date fechaWeb;   
}
