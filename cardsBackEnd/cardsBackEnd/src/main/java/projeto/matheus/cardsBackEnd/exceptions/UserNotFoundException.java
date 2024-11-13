package projeto.matheus.cardsBackEnd.exceptions;

public class UserNotFoundException extends RuntimeException {

public UserNotFoundException(String message) {
	super(message);
}
}
