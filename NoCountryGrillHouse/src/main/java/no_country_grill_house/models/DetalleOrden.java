package no_country_grill_house.models;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import no_country_grill_house.models.enums.Estado_Platillo;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "detalleorden")
public class DetalleOrden implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="detalleorden_sequence")
    @SequenceGenerator(name="detalleorden_sequence", sequenceName="detalleorden_sequence", allocationSize=100)   
    private Long idDetalle;
    
    private int cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado_Platillo estado_Platillo;

    @ManyToOne
    @JoinColumn(name = "id_orden",referencedColumnName = "numeroOrden")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "id_platillo",referencedColumnName = "id")
    private Platillo platillo;
    
}