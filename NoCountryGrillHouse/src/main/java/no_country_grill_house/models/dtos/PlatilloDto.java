package no_country_grill_house.models.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Categoria;
import no_country_grill_house.models.Cliente;
import no_country_grill_house.models.FotoPlatillo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatilloDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private String tiempoEstimado;
    private Boolean alta;
    private Categoria categoria;
    private List<Cliente> clientes;
    private FotoPlatillo foto;

}
