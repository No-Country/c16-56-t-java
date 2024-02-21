package no_country_grill_house.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "direccion")
public class Direccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direccion_sequence")
    @SequenceGenerator(name = "direccion_sequence", sequenceName = "direccion_sequence", allocationSize = 100)
    private Long id;
    @Column(name = "calle", nullable = false)
    @NotBlank
    private String calle;
    @Column(name = "numero", nullable = false)
    private String numero;
    @Column(name = "ciudad", nullable = false)
    private String ciudad;
    @Column(name = "alta", nullable = false)
    private Boolean alta;

}