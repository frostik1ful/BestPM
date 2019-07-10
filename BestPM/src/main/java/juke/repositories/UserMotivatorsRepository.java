package juke.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.User;
import juke.entities.UserMotivators;

public interface UserMotivatorsRepository extends JpaRepository<UserMotivators, Integer> {

	public List<UserMotivators> findAllByUser(User user);

	public List<UserMotivators> findByUserAndIsActive(User user, boolean isActive);

}
