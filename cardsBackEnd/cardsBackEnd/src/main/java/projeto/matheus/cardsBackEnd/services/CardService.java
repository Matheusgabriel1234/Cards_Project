package projeto.matheus.cardsBackEnd.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.data.geo.Distance;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import projeto.matheus.cardsBackEnd.dtos.CardEntityDTO;
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


public CardEntityDTO addCard(CardEntityDTO cardEntityDto) {
UserEntity userAuthenticated = getAuthenticatedUser();
CardEntity cardEntity = convertToEntity(cardEntityDto);
cardEntity.setUser(userAuthenticated);
CardEntity savedcard = cardRepo.save(cardEntity);
return convertToDTO(cardEntity);

}


public List<CardEntityDTO> getAllUsersCard(){
	UserEntity user = getAuthenticatedUser();
	List<CardEntity> cardEntities = cardRepo.findByUser(user);
	
	return cardEntities.stream().map(this::convertToDTO).collect(Collectors.toList());
}

private CardEntity convertToEntity(CardEntityDTO cardDTO) {
    CardEntity cardEntity = new CardEntity();
    cardEntity.setName(cardDTO.getName());
    cardEntity.setMaskedcardNumber(cardDTO.getMaskedCardNumber());
    cardEntity.setCreditLimit(cardDTO.getCreditLimit());
    cardEntity.setAvailiableLimit(cardDTO.getAvaliableLimit());
    cardEntity.setEmissorBank(cardDTO.getEmissorBank());
    cardEntity.setBillExpireDate(cardDTO.getBillExpireDate());
    cardEntity.setCardType(cardDTO.getCardType());
    return cardEntity;
}

private CardEntityDTO convertToDTO(CardEntity cardEntity) {
    CardEntityDTO cardDTO = new CardEntityDTO();
    cardDTO.setName(cardEntity.getName());
    cardDTO.setMaskedCardNumber(cardEntity.getMaskedcardNumber());
    cardDTO.setCreditLimit(cardEntity.getCreditLimit());
    cardDTO.setAvaliableLimit(cardEntity.getAvailiableLimit());
    cardDTO.setEmissorBank(cardEntity.getEmissorBank());
    cardDTO.setBillExpireDate(cardEntity.getBillExpireDate());
    cardDTO.setCardType(cardEntity.getCardType());
    return cardDTO;
}



}
