package projeto.matheus.cardsBackEnd.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.matheus.cardsBackEnd.entities.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long>  {

Optional<UserEntity> findByEmail(String email);
}
