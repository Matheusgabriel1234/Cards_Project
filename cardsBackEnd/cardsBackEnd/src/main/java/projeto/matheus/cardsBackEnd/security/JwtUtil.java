package projeto.matheus.cardsBackEnd.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import projeto.matheus.cardsBackEnd.entities.UserEntity;

@Component
public class JwtUtil {

@Value("${jwt.secret}")
private String jwtSecret;

@Value("${jwt.expiration}")
private int jwtExpiration;

public String generateToken(UserEntity user) {
	return Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
	.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
}

public String getEmailFromJwtToken(String token) {
	return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
			.getBody()
			.getSubject();
}

public boolean validateJwtToken(String authToken) {
    try {
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);  // Verifica a assinatura e integridade do token
        return true;
    } catch (SignatureException e) {
        System.err.println("Assinatura JWT inválida: " + e.getMessage());
    } catch (MalformedJwtException e) {
        System.err.println("Token JWT malformado: " + e.getMessage());
    } catch (ExpiredJwtException e) {
        System.err.println("Token JWT expirado: " + e.getMessage());
    } catch (UnsupportedJwtException e) {
        System.err.println("Token JWT não suportado: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        System.err.println("JWT vazio ou apenas espaços em branco: " + e.getMessage());
    }

    return false;
}


}

