package projeto.matheus.cardsBackEnd.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorDetails {
private LocalDateTime timestamp;
private int status;
private String error;
private String message;
private String path;
private List<ValidationError> errors;

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ValidationError> getErrors() {
		return errors;
	}

	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}

	public ErrorDetails() {
	this.timestamp = LocalDateTime.now();
}

public ErrorDetails(int status, String error, String message, String path) {
    this();
    this.status = status;
    this.error = error;
    this.message = message;
    this.path = path;
}

public ErrorDetails(int status, String error, String message, String path, List<ValidationError> errors) {
    this(status, error, message, path);
    this.errors = errors;
}

public static class ValidationError {
private String field;
private String message;

public ValidationError(){
	
}

public ValidationError(String field, String message) {
	super();
	this.field = field;
	this.message = message;
}

public String getField() {
	return field;
}

public void setField(String field) {
	this.field = field;
}

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}


}


}
