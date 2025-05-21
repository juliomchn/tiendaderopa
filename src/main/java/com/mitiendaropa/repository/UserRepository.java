package com.mitiendaropa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mitiendaropa.model.User;

import java.util.Optional;

// UserRepository extiende JpaRepository para proporcionar métodos CRUD básicos
// para la entidad User.
// <User, Long> indica que es para la entidad User y que su ID es de tipo Long.
public interface UserRepository extends JpaRepository<User, Long> {

    // Método personalizado para buscar un usuario por su nombre de usuario.
    // Spring Data JPA automáticamente implementará este método.
    Optional<User> findByUsername(String username);

    // Método personalizado para buscar un usuario por su correo electrónico.
    Optional<User> findByEmail(String email);

    // Puedes añadir más métodos de búsqueda según tus necesidades.
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = ?1 OR u.email = ?2")
    Optional<User> findByUsernameOrEmail(String username, String email);
}