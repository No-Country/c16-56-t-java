package no_country_grill_house.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "direccion")
public class Direccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direccion_sequence")
    @SequenceGenerator(name = "direccion_sequence", sequenceName = "direccion_sequence", allocationSize = 100)
    private Long id;
    @Column(name = "calle", nullable = false)
    private String calle;
    @Column(name = "numero", nullable = false)
    private String numero;
    @Column(name = "ciudad", nullable = false)
    private String ciudad;

}