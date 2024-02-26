package no_country_grill_house.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import no_country_grill_house.models.enums.Estado_Reserva;

import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserv_seq")
    @SequenceGenerator(name = "reserv_seq", sequenceName = "reserv_seq", allocationSize = 100)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @Column(name = "fechaHora", nullable = false)
    private LocalTime fechaHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado_Reserva estado_reserva;
    
    @Column(name = "fechaWeb", nullable = false)
    private Date fechaWeb;    
}
