package no_country_grill_house.models;

import java.util.Date;
import java.io.Serializable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "orden")
public class Orden implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orden_sequence")
    @SequenceGenerator(name = "orden_sequence", sequenceName = "orden_sequence", allocationSize = 100)
    private Long numeroOrden;

    /*
     * @OneToMany
     * 
     * @JoinColumn(name = "numeroMesa", referencedColumnName = "numeroMesa")
     * private Mesa mesa;
     */

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "id_reserva", referencedColumnName = "id")
    private Reserva reserva;

    

    /*
     * @ManyToMany
     * 
     * @JoinTable(name = "detalle_orden",
     * joinColumns = @JoinColumn(name = "orden_id", referencedColumnName =
     * "numeroOrden"),
     * inverseJoinColumns = @JoinColumn(name = "platillo_id", referencedColumnName =
     * "id")
     * private List<Platillo> platillos;
     */

    private Date fecha;
    private boolean status;

}