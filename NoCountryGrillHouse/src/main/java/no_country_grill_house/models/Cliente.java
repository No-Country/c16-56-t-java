package no_country_grill_house.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente extends Usuario {

    @ManyToMany
    @JoinTable(name = "cliente_platillo", joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "platillo_id", referencedColumnName = "id"))
    @JsonIgnore
    private List<Platillo> favoritos;

}