package projeto.matheus.cardsBackEnd.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	

@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex,WebRequest req){
ErrorDetails errorDetails = new ErrorDetails(
HttpStatus.NOT_FOUND.value(),
"Usuario não encontrado",
ex.getMessage(),
req.getDescription(false).replace("uri=", "")

);
return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	
}

@ExceptionHandler(InvalidDataException.class)
public ResponseEntity<ErrorDetails> handleInvalidDataException(InvalidDataException ex, WebRequest req){
ErrorDetails errorDetails = new ErrorDetails(
HttpStatus.BAD_REQUEST.value(),
"Dados Invalidos",
ex.getMessage(),req.getDescription(false).replace("uri=", "")
);
return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(ObjectNotFoundException.class)
public ResponseEntity<ErrorDetails> handleObjectNotFoundException(ObjectNotFoundException ex, WebRequest req){
ErrorDetails errorDetails = new ErrorDetails(
HttpStatus.NOT_FOUND.value(),
"Cartão não encontrado",
ex.getMessage(),req.getDescription(false).replace("uri=", ""));
return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);	
}

@ExceptionHandler(EmailAlreadyExistsException.class)
public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex,WebRequest req){
ErrorDetails errorDetails = new ErrorDetails(
HttpStatus.CONFLICT.value(),
"Esse email ja existe",
ex.getMessage(),req.getDescription(false).replace("uri=", ""));
return new ResponseEntity<>(errorDetails,HttpStatus.CONFLICT);	
}

@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorDetails> handleValidationException(MethodArgumentNotValidException ex, WebRequest req){
List<ErrorDetails.ValidationError> validationErrors = new ArrayList<>();

ex.getBindingResult().getFieldErrors().forEach((error) -> {
	String fieldName = error.getField();
	String message = error.getDefaultMessage();
	validationErrors.add(new ErrorDetails.ValidationError(fieldName,message));
});

ErrorDetails errorDetails = new ErrorDetails(
		  HttpStatus.BAD_REQUEST.value(),
          "Falha na validação",
          "Erro de validação nos campos",
          req.getDescription(false).replace("uri=", ""),
          validationErrors		
		
		
);

return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);


}

}
