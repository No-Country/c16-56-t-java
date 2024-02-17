package no_country_grill_house.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "platillo")
public class Platillo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "platillo_sequence")
    @SequenceGenerator(name = "platillo_sequence", sequenceName = "platillo_sequence", allocationSize = 100)
    private Long id;
    private String nombre;
    private StringBuilder descripcion;
    private double precio;
    private String tiempoEstimado;
    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;
    @ManyToMany(mappedBy = "favoritos", cascade = CascadeType.REMOVE)
    private List<Cliente> clientes;

}