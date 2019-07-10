package juke.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.Programmer;
import juke.entities.User;
import utils.Rating;

public interface ProgrammerRepository extends JpaRepository<Programmer, Integer> {

	public Integer countByIsHiredFalse();

	public Integer countByRatingAndIsHiredFalse(Rating rating);

	public Integer countByUser(User user);

	public List<Programmer> findAllByRatingAndIsHiredFalse(Rating rating);

	public List<Programmer> findAllByUser(User user);

	public Optional<Programmer> findByNameAndIsHiredFalseAndRating(String name, Rating rating);

	public Programmer findById(int programmerId);
	
	public List<Programmer> findAllByOrderById();

}
