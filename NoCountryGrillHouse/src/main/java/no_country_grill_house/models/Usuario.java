package no_country_grill_house.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no_country_grill_house.models.enums.Rol;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "nombre", nullable = false)
    protected String nombre;
    @Column(name = "email", nullable = false)
    protected String email;
    @Column(name = "password", nullable = false)
    protected String password;
    @Column(name = "telefono")
    protected String telefono;
    @Column(name = "fecha_alta", nullable = false)
    protected LocalDateTime fechaAlta;
    @Column(name = "alta", nullable = false)
    protected Boolean alta;
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    protected Rol rol;
    @ManyToOne
    @JoinColumn(name = "direccion", referencedColumnName = "id")
    protected Direccion direccion;
    @OneToOne
    @JoinColumn(name = "id_foto", referencedColumnName = "id")
    protected FotoUsuario foto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}