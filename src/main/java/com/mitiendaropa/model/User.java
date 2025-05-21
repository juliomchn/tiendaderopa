package com.mitiendaropa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection; // Para roles/autoridades
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data // Genera getters, setters, toString, equals y hashCode con Lombok
@NoArgsConstructor // Genera un constructor sin argumentos con Lombok
@AllArgsConstructor // Genera un constructor con todos los argumentos con Lombok
@Builder // Permite construir objetos de forma fluida (User.builder()...build())
@Entity // Marca esta clase como una entidad JPA
@Getter
@Setter
@Table(name = "users") // Especifica el nombre de la tabla en la base de datos
public class User implements UserDetails { // Implementa UserDetails para Spring Security

    @Id // Marca la propiedad como la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generación automática de IDs
    private Long id;

    @Column(nullable = false, unique = true) // No nulo y debe ser único
    private String username;

    @Column(nullable = false, unique = true) // No nulo y debe ser único
    private String email;

    @Column(nullable = false) // La contraseña se guardará encriptada
    private String password;

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    // Métodos de UserDetails que deben ser implementados.
    // Para la mayoría de las aplicaciones, se pueden dejar en 'true'.
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .toList();
    }




}