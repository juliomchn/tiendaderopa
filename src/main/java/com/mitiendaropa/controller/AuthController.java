package com.mitiendaropa.controller;


import com.mitiendaropa.dto.LoginRequest;
import com.mitiendaropa.dto.UserRegistrationDTO;
import com.mitiendaropa.dto.UserResponseDTO;
import com.mitiendaropa.model.User;
import com.mitiendaropa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marca esta clase como un controlador REST
@RequestMapping("/api/auth") // Ruta base para los endpoints de autenticación
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    // Si usas JWT, necesitarías un JwtTokenProvider aquí (que aún no hemos creado)

    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
   public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) {
        try {
            User registeredUser = userService.registerUser(userRegistrationDTO);
            UserResponseDTO responseDTO = UserResponseDTO.builder()
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .email(registeredUser.getEmail())
                    .build();
            System.out.println(responseDTO.getUsername() + "JULIO");
            return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Manejo de errores específicos, por ejemplo, usuario/email ya existe
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Manejo de otros errores inesperados
            return new ResponseEntity("Error al registrar usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginRequest loginDTO) {
        try {
            // Autentica al usuario usando el AuthenticationManager de Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUsernameOrEmail(), // Puede ser username o email
                            loginDTO.getPassword()
                    )
            );

            // Establece la autenticación en el contexto de seguridad (opcional si usas JWT)
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // *** Aquí es donde generarías y devolverías el token JWT ***
            // Por ahora, solo devolvemos un mensaje de éxito.
            // String jwt = jwtTokenProvider.generateToken(authentication);
            // return ResponseEntity.ok(new JwtAuthResponse(jwt));

            return new ResponseEntity<>("Inicio de sesión exitoso", HttpStatus.OK);

        } catch (Exception e) {
            // Captura AuthenticationException (ej. BadCredentialsException)
            return new ResponseEntity<>("Credenciales inválidas: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}