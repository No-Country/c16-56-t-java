package no_country_grill_house.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

import no_country_grill_house.models.Admin;
import no_country_grill_house.models.dtos.AdminDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@Component
public interface AdminMapper {

    @Mapping(target = "authorities", ignore = true)
    Admin toAdmin(AdminDto adminDto);

    @Mapping(target = "password2", ignore = true)
    AdminDto toAdminDto(Admin admin);

}
