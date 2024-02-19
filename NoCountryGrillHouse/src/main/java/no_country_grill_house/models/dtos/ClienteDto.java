package no_country_grill_house.models.dtos;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.FotoUsuario;
import no_country_grill_house.models.Platillo;
import no_country_grill_house.models.enums.Rol;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

  private Long id;
  private String nombre;
  private String email;
  private String password;
  private String telefono;
  private LocalDateTime fechaAlta;
  private Boolean alta;
  private Rol rol;
  private Direccion direccion;
  private List<Platillo> favoritos;
  private FotoUsuario foto;

}
