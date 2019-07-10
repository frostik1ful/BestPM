package juke.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

	Event findByName(String name);
	List<Event> findAll();

}
