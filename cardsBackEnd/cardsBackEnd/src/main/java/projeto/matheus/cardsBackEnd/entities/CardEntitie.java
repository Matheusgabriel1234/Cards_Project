package projeto.matheus.cardsBackEnd.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cards")
public class CardEntitie {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private String name;

@Column(nullable = false)
private String cardNumber;

private double creditLimit;

private double availiableLimit;

private String emissorBank;

private LocalDate billExpireDate;

@Enumerated(EnumType.STRING)
private CardType cardType;

@ManyToOne
@JoinColumn(name = "usuario_id",nullable = false)
private UserEntitie user;

public CardEntitie(Long id, String name, String cardNumber, double creditLimit, double availiableLimit,
		String emissorBank, LocalDate billExpireDate, CardType cardType, UserEntitie user) {

	this.id = id;
	this.name = name;
	this.cardNumber = cardNumber;
	this.creditLimit = creditLimit;
	this.availiableLimit = availiableLimit;
	this.emissorBank = emissorBank;
	this.billExpireDate = billExpireDate;
	this.cardType = cardType;
	this.user = user;
}

public CardEntitie() {

}

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getCardNumber() {
	return cardNumber;
}

public void setCardNumber(String cardNumber) {
	this.cardNumber = cardNumber;
}

public double getCreditLimit() {
	return creditLimit;
}

public void setCreditLimit(double creditLimit) {
	this.creditLimit = creditLimit;
}

public double getAvailiableLimit() {
	return availiableLimit;
}

public void setAvailiableLimit(double availiableLimit) {
	this.availiableLimit = availiableLimit;
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

public UserEntitie getUser() {
	return user;
}

public void setUser(UserEntitie user) {
	this.user = user;
}






}
