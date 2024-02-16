package no_country_grill_house.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import no_country_grill_house.models.Usuario;

@Repository
public interface UsuarioRepositoryDos extends JpaRepository<Usuario, Long> {

}
