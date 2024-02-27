package no_country_grill_house.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private final JwtFilter jwtFilter;

    @Autowired
    private final AuthenticationProvider authenticationProvider;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/v1/authenticate", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                            .permitAll();
                    auth.requestMatchers(endPointsPublicos()).permitAll().anyRequest().authenticated();
                })
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private RequestMatcher endPointsPublicos() {
        return new OrRequestMatcher(
                new AntPathRequestMatcher("/cliente/registrar"),
                new AntPathRequestMatcher("/admin/registrar"),
                new AntPathRequestMatcher("/jefe_cocina/registrar"),
                new AntPathRequestMatcher("/cliente"),
                new AntPathRequestMatcher("/admin"),
                new AntPathRequestMatcher("/jefe_cocina"),
                new AntPathRequestMatcher("/auth/login"),
                new AntPathRequestMatcher("/"),
                new AntPathRequestMatcher("/js/*"),
                new AntPathRequestMatcher("/css/*"),
                new AntPathRequestMatcher("/images/**"),
                new AntPathRequestMatcher("/images/favicon/*"),
                new AntPathRequestMatcher("/images/Logo/*"));
    }

}
