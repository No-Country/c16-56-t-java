package no_country_grill_house.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cliente")
public class Cliente extends Usuario {

    @ManyToMany
    @JoinTable(name = "cliente_platillo", joinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "platillo_id", referencedColumnName = "id"))
    private List<Platillo> favoritos;

}