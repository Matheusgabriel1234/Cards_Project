package projeto.matheus.cardsBackEnd.entities;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_users")
public class UserEntity implements UserDetails {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private String firstName;

@Column(nullable = false)
private String lastName;

@Column(nullable = false)
private String email;

@Column(nullable = false)
private String password;

public UserEntity() {
	
}






public UserEntity(Long id, String firstName, String lastName, String email, String password) {
	super();
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.password = password;
}






public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}


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

@Override
public int hashCode() {
	return Objects.hash(id);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	UserEntity other = (UserEntity) obj;
	return Objects.equals(id, other.id);
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {

	return null;
}

@Override
public String getUsername() {
	
	return this.email;
}

@Override
public boolean isAccountNonExpired() {
    return true; 
}

@Override
public boolean isAccountNonLocked() {
    return true;
}

@Override
public boolean isCredentialsNonExpired() {
    return true; 
}

@Override
public boolean isEnabled() {
    return true; 
}



}
