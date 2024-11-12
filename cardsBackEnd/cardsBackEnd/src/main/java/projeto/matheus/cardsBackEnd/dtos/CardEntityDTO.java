package projeto.matheus.cardsBackEnd.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import projeto.matheus.cardsBackEnd.entities.CardEntity;
import projeto.matheus.cardsBackEnd.entities.CardType;

public class CardEntityDTO {
private String name;
@Size(max = 4, message = "Digite apenas os ultimos 4 numeros do cartão")
@Pattern(regexp = "\\d{4}",message = "Digite os ultimos 4 numeros do cartão")
private String maskedCardNumber;
private double creditLimit;
private double avaliableLimit;
private String emissorBank;
private LocalDate billExpireDate;
private CardType cardType;


public CardEntityDTO() {
	
}





public CardEntityDTO(String name, String maskedCardNumber, double creditLimit, double avaliableLimit,
		String emissorBank, LocalDate billExpireDate, CardType cardType) {
	super();
	this.name = name;
	this.maskedCardNumber = maskedCardNumber;
	this.creditLimit = creditLimit;
	this.avaliableLimit = avaliableLimit;
	this.emissorBank = emissorBank;
	this.billExpireDate = billExpireDate;
	this.cardType = cardType;
}





public void setMaskedCardNumber(String maskedCardNumber) {
	this.maskedCardNumber = maskedCardNumber;
}





public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public String getMaskedCardNumber() {
	return maskedCardNumber;
}

public double getCreditLimit() {
	return creditLimit;
}


public void setCreditLimit(double creditLimit) {
	this.creditLimit = creditLimit;
}


public double getAvaliableLimit() {
	return avaliableLimit;
}


public void setAvaliableLimit(double avaliableLimit) {
	this.avaliableLimit = avaliableLimit;
}


public String getEmissorBank() {
	return emissorBank;
}


public void setEmissorBank(String emissorBank) {
	this.emissorBank = emissorBank;
}



public LocalDate getBillExpireDate() {
	return billExpireDate;
}





public void setBillExpireDate(LocalDate billExpireDate) {
	this.billExpireDate = billExpireDate;
}





public CardType getCardType() {
	return cardType;
}


public void setCardType(CardType cardType) {
	this.cardType = cardType;
}






}
