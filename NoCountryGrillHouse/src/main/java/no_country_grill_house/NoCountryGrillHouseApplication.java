package no_country_grill_house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "package no_country_grill_house.repositories",
		"package no_country_grill_house.controllers", "package no_country_grill_house.exceptions",
		"package no_country_grill_house.mappers", "package no_country_grill_house.models",
		"package no_country_grill_house.models.dtos", "package no_country_grill_house.models.enums",
		"package no_country_grill_house.services", "package no_country_grill_house.resources",
		"package no_country_grill_house.config" })
@SpringBootApplication
public class NoCountryGrillHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoCountryGrillHouseApplication.class, args);
	}

}
