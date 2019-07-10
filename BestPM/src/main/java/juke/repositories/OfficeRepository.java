package juke.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.Office;

public interface OfficeRepository extends JpaRepository<Office, Integer> {
	
	public Optional<Office> findByLevel(Integer level);
	public Optional<Office> findById(Integer id);

}
