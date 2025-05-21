package com.mitiendaropa.service.impl;



import com.mitiendaropa.dto.UserRegistrationDTO;
import com.mitiendaropa.exception.ResourceNotFoundException;
import com.mitiendaropa.model.Role;
import com.mitiendaropa.model.User;
import com.mitiendaropa.repository.RoleRepository;
import com.mitiendaropa.repository.UserRepository;
import com.mitiendaropa.service.UserService;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service // Marca esta clase como un componente de servicio de Spring
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository; // Inyectar RoleRepository

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
@Transactional
public User registerUser(UserRegistrationDTO registrationDTO) {
    if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
        throw new IllegalArgumentException("El nombre de usuario ya existe.");
    }
    if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
        throw new IllegalArgumentException("El correo electrónico ya existe.");
    }

    Role userRole = roleRepository.findByName("ROLE_USER")
        .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado"));

    User user = User.builder()
            .username(registrationDTO.getUsername())
            .email(registrationDTO.getEmail())
            .password(passwordEncoder.encode(registrationDTO.getPassword()))
            .roles(Set.of(userRole))
            .build();

    return userRepository.save(user);
}

    


    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    // Método requerido por UserDetailsService para cargar los detalles del usuario
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(usernameOrEmail)
                    .or(() -> userRepository.findByEmail(usernameOrEmail))
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getRoles().stream()
                 .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList())
        );
    }


}