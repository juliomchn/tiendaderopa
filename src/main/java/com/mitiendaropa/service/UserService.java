package com.mitiendaropa.service;

import com.mitiendaropa.dto.UserRegistrationDTO;
import com.mitiendaropa.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User registerUser(UserRegistrationDTO userDTO);
    User save(User user);
}
