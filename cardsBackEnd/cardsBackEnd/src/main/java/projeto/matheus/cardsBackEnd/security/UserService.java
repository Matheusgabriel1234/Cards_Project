package projeto.matheus.cardsBackEnd.security;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;




import jakarta.transaction.Transactional;
import projeto.matheus.cardsBackEnd.entities.UserEntity;
import projeto.matheus.cardsBackEnd.exceptions.EmailAlreadyExistsException;
import projeto.matheus.cardsBackEnd.exceptions.InvalidDataException;
import projeto.matheus.cardsBackEnd.exceptions.UserNotFoundException;
import projeto.matheus.cardsBackEnd.repositories.UserEntityRepository;

@Service
public class UserService {

@Autowired
private JwtUtil jwtUtil;

private UserEntityRepository userRepo;

@Autowired
private PasswordEncoder passwordEncoder;

public UserService(UserEntityRepository userRepo) {
	super();
	this.userRepo = userRepo;
}

@Transactional
public UserEntity registerUser(String email,String password,String firstName,String lastName) {

		
UserEntity usuario = new UserEntity();

if(userRepo.existsByEmail(email)){
    throw new EmailAlreadyExistsException("Esse email ja foi cadastrado");
}

usuario.setFirstName(firstName);
usuario.setLastName(lastName);
usuario.setEmail(email);
usuario.setPassword(passwordEncoder.encode(password));


return userRepo.save(usuario);
}

public String authenticateUser(String email, String password) {
    Optional<UserEntity> optionalUser = userRepo.findByEmail(email);

    if (optionalUser.isEmpty() || !passwordEncoder.matches(password, optionalUser.get().getPassword())) {
        throw new InvalidDataException("Credenciais inválidas");
    }

    return jwtUtil.generateToken(optionalUser.get());
}



public Long getbyEmail(String email) throws Exception{
return userRepo.findByEmail(email).map(UserEntity::getId).orElseThrow(()-> new UserNotFoundException("O user com o email " + email +  " não existe"));
}


}