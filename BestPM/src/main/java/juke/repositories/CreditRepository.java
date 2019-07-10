package juke.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.Credit;
import juke.entities.User;

public interface CreditRepository extends JpaRepository<Credit, Integer> {
	
	public Optional<Credit> findByUser(User user);

}
