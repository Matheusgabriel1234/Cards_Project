package projeto.matheus.cardsBackEnd.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.matheus.cardsBackEnd.dtos.CardEntityDTO;
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
public ResponseEntity<List<CardEntityDTO>> getAllUserCard(){
	List<CardEntityDTO> cards = cardService.getAllUsersCard();
	return ResponseEntity.ok().body(cards);
	}

@PostMapping
public ResponseEntity<CardEntityDTO> addCards(@RequestBody CardEntityDTO card){
	CardEntityDTO savedCard = cardService.addCard(card);
	return  ResponseEntity.status(201).body(savedCard);
}
}
