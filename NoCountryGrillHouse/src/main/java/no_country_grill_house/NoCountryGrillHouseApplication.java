package no_country_grill_house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = { "package no_country_grill_house.repositories;" })
public class NoCountryGrillHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoCountryGrillHouseApplication.class, args);
	}

}
