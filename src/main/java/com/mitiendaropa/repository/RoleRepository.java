// mi-tienda-ropa-backend/src/main/java/com/mitiendaropa/repository/RoleRepository.java
package com.mitiendaropa.repository;

import com.mitiendaropa.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}