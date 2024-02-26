package no_country_grill_house.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "mesa")
public class Mesa  {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="mesa_sequence")
    @SequenceGenerator(name="mesa_sequence", sequenceName="mesa_sequence", allocationSize=100)
    private Long  meroMesa;
    
}          

