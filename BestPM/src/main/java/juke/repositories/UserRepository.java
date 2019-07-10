package juke.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByUsername(String username);

	public Integer findUserIdByUsername(String username);

	public User findByEmail(String email);

}