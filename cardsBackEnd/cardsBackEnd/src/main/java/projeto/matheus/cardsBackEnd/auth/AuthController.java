package projeto.matheus.cardsBackEnd.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import projeto.matheus.cardsBackEnd.dtos.LoginRequestDTO;
import projeto.matheus.cardsBackEnd.dtos.SignUpDto;
import projeto.matheus.cardsBackEnd.security.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpDto registrationDto) {
        try {
            userService.registerUser(registrationDto.getEmail(), registrationDto.getPassword(),registrationDto.getFirstName(),registrationDto.getLastName());
            return ResponseEntity.ok("Usuário registrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody  LoginRequestDTO loginDto) {
        try {
            String token = userService.authenticateUser(loginDto.getEmail(), loginDto.getPassword());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
}