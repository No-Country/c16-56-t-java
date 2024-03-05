package no_country_grill_house.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "mesa")
public class Mesa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "numero", nullable = false)
    private Integer numero;
    @Column(name = "cantidad_personas", nullable = false)
    private Integer cantidadPersonas;
    @Column(name = "alta", nullable = false)
    private Boolean alta;
    @ManyToOne
    @JoinColumn(name = "id_mesero", referencedColumnName = "id")
    private Mesero mesero;

}
