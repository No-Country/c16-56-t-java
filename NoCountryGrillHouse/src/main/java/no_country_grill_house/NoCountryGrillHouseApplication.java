package no_country_grill_house;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class NoCountryGrillHouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoCountryGrillHouseApplication.class, args);
	}

}
