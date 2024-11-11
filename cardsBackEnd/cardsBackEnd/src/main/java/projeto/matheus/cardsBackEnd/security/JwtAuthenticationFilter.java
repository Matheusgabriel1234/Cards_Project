package projeto.matheus.cardsBackEnd.security;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import projeto.matheus.cardsBackEnd.entities.UserEntity;
import projeto.matheus.cardsBackEnd.repositories.UserEntityRepository;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserEntityRepository userRepo;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, FilterChain filterChain)
                                    throws ServletException, IOException {

        String path = request.getRequestURI();

       
        if (path.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
               
                String email = jwtUtil.getEmailFromJwtToken(jwt);
                
                
                UserEntity userEntity = userRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));

                
                UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(userEntity.getEmail())
                    .password(userEntity.getPassword())
                    .authorities(new ArrayList<>()) 
                    .build();

                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Usuário {} autenticado com sucesso", email);
            }
        } catch (Exception e) {
            logger.error("Não foi possível definir a autenticação do usuário no contexto de segurança", e);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Erro na autenticação JWT: " + e.getMessage() + "\"}");
            response.getWriter().flush();
            return;
        }

     
        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        logger.debug("Authorization header received: {}", headerAuth);

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            String token = headerAuth.substring(7);
            logger.debug("Token JWT extraído: {}", token);
            return token;
        }

        return null;
    }
}
