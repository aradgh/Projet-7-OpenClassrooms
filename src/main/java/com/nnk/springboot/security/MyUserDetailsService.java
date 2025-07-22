package com.nnk.springboot.security;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Charge un utilisateur par son nom d'utilisateur.
     * Utilisé par Spring Security lors du processus d'authentification.
     *
     * @param username le nom d'utilisateur de l'utilisateur à rechercher
     * @return un objet {@link UserDetails} contenant les informations de sécurité de l'utilisateur
     * @throws UsernameNotFoundException si aucun utilisateur correspondant n'est trouvé
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
