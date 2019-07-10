package juke.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import juke.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
