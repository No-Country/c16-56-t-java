package no_country_grill_house.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name = "platillo")
public class Platillo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @Column(name = "descripcion", nullable = false)
    private StringBuilder descripcion;
    @Column(name = "precio", nullable = false)
    private Double precio;
    @Column(name = "tiempo_estimado", nullable = false)
    private String tiempoEstimado;
    @Column(name = "alta", nullable = false)
    private Boolean alta;
    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private Categoria categoria;
    @ManyToMany(mappedBy = "favoritos", cascade = CascadeType.REMOVE)
    private List<Cliente> clientes;
    @OneToOne
    @JoinColumn(name = "id_foto", referencedColumnName = "id")
    private FotoPlatillo foto;
}