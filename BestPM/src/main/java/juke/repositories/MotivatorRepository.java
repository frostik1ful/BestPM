package juke.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.Motivator;

public interface MotivatorRepository extends JpaRepository<Motivator, Integer> {

	public Optional<Motivator> findByName(String name);

}
