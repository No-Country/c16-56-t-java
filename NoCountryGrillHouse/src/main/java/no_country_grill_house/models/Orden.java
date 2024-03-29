package no_country_grill_house.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orden")
public class Orden implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "fecha_alta", nullable = false)
    private LocalDateTime fechaAlta;
    @Column(name = "alta", nullable = false)
    private Boolean alta;

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

    private boolean status;

}