package projeto.matheus.cardsBackEnd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import projeto.matheus.cardsBackEnd.entities.UserEntity;
import projeto.matheus.cardsBackEnd.repositories.UserEntityRepository;

@Service
public class CustomUserDetails implements UserDetailsService{
@Autowired
private UserEntityRepository userRepo;

@Override
public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
UserEntity user = userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Usuario não encotrado com o email" + email));
return user;
		
	}
}
