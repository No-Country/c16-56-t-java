package no_country_grill_house.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FotoPlatilloDto {

    private Long id;
    private String nombre;
    private String url;
    private Boolean alta = true;
    private String fotoId;

}
