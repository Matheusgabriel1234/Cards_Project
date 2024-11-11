package projeto.matheus.cardsBackEnd.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import projeto.matheus.cardsBackEnd.entities.CardEntity;
import projeto.matheus.cardsBackEnd.entities.UserEntity;

public interface CardRepository extends JpaRepository<CardEntity, Long>{
List<CardEntity> findByUser(UserEntity user);
}
