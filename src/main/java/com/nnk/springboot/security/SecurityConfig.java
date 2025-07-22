package com.nnk.springboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure la chaîne de filtres de sécurité HTTP pour les requêtes Web.
     *
     * Définit les règles d'autorisation, de connexion, de déconnexion, et de gestion de session.
     *
     * @param http l'objet {@link HttpSecurity} utilisé pour configurer la sécurité
     * @return la chaîne de filtres {@link SecurityFilterChain} configurée
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/","/home","/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/app/login") // C'est ici que le formulaire de connexion est affiché
                .loginProcessingUrl("/login") // C'est ici que les données sont envoyées. Spring Security intercepte le POST pour faire l'authentification
                .defaultSuccessUrl("/bidList/list", false)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .sessionManagement(session -> session
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            );
        return http.build();
    }

    /**
     * Fournit le bean {@link PasswordEncoder} pour encoder les mots de passe utilisateurs.
     *
     * Utilise ici l'algorithme BCrypt.
     *
     * @return le bean {@link PasswordEncoder}
     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

