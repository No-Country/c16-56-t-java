package no_country_grill_house.models.dtos;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import no_country_grill_house.models.Direccion;
import no_country_grill_house.models.Platillo;
import no_country_grill_house.models.enums.Rol;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDto {

  private Long id;
  private String nombre;
  private String email;
  private String password;
  private String telefono;
  private Date fechaAlta;
  private Boolean alta;
  private Rol rol;
  private Direccion direccion;
  private List<Platillo> favoritos;

}
