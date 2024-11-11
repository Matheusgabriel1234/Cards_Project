package projeto.matheus.cardsBackEnd.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignUpDto {

@NotBlank(message = "O nome não deve estar em branco")
private String firstName;

@NotBlank(message = "O nome não deve estar em branco")
private String lastName;

@Email(message = "deve seguir o formato de um email valido")
@NotBlank(message = "O email não pode estar em branco")
private String email;

@NotBlank(message = "A senha não pode estar vazia")
@Size(min = 8,message = "A senha deve ter no minimo 8 caracteres")
private String password;




public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}


}
