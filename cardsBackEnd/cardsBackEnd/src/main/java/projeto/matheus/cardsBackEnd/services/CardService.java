package projeto.matheus.cardsBackEnd.services;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.springframework.data.geo.Distance;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.matheus.cardsBackEnd.entities.CardEntity;
import projeto.matheus.cardsBackEnd.entities.UserEntity;
import projeto.matheus.cardsBackEnd.repositories.CardRepository;
import projeto.matheus.cardsBackEnd.repositories.UserEntityRepository;

@Service
public class CardService {

private CardRepository cardRepo;

private UserEntityRepository userRepo;




public CardService(CardRepository cardRepo, UserEntityRepository userRepo) {
	super();
	this.cardRepo = cardRepo;
	this.userRepo = userRepo;
}

public UserEntity getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()
            || authentication instanceof AnonymousAuthenticationToken) {
        throw new RuntimeException("Usuário não autenticado");
    }

    String email = authentication.getName();
    return userRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + email));
}


public CardEntity addCard(CardEntity cardEntity) {
    UserEntity userAuthenticated = getAuthenticatedUser();
    cardEntity.setUser(userAuthenticated);
    return cardRepo.save(cardEntity);
}

public List<CardEntity> getUsersCard(){
UserEntity user = getAuthenticatedUser();
return cardRepo.findByUser(user);
}



}
