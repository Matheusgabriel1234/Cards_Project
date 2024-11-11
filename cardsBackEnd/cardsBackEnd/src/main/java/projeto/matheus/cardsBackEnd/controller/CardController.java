package projeto.matheus.cardsBackEnd.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.matheus.cardsBackEnd.entities.CardEntity;
import projeto.matheus.cardsBackEnd.services.CardService;

@RequestMapping("api/cards")
@RestController
public class CardController {

private CardService cardService;

public CardController(CardService cardService) {
	super();
	this.cardService = cardService;
}


@GetMapping("/my-cards")
public ResponseEntity<List<CardEntity>> getAllUserCard(){
	List<CardEntity> cards = cardService.getUsersCard();
	return ResponseEntity.ok().body(cards);
	}

@PostMapping
public ResponseEntity<CardEntity> addCards(@RequestBody CardEntity card){
	CardEntity savedCard = cardService.addCard(card);
	return  ResponseEntity.status(201).body(savedCard);
}
}
