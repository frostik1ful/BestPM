package juke.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.Project;
import utils.TypeOfPayment;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

	public Optional<Project> findByName(String name);

	public Optional<Project> findByNameAndUserIsNull(String name);

	public List<Project> findByDifficultLessThanEqual(Float difficult);

	public Integer countByDifficultLessThanEqual(Float difficult);

	public Integer countByTypeOfPayment(TypeOfPayment typeOfPayment);

	public List<Project> findByUserIsNullAndDifficultGreaterThanAndDifficultLessThanEqual(Float minDifficult,
			Float maxDifficult);

}
