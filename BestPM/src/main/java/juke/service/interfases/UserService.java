package juke.service.interfases;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import juke.entities.Motivator;
import juke.entities.Office;
import juke.entities.Programmer;
import juke.entities.Project;
import juke.entities.User;
import juke.entities.UserMotivators;

public interface UserService {

	public void save(User user);

	public User findByUsername(String username);

	public User findByEmail(String email);

	public void addProject(String username, int projectId);

	public Set<Project> getProjects(String username);

	public boolean addOffice(String username, Office office);

	public boolean addOffice(String username, int level);

	public boolean hireProgrammer(String username, Programmer programmer, int price);

	public boolean hireProgrammer(String username, int programmerId, int price);

	public List<Programmer> getUserProgrammers(String username);

	public boolean buyMotivator(String username, Motivator motivator);

	public List<UserMotivators> getUserMotivators(String username, boolean active);

	public boolean fireProgrammer(int programmerId, String name);

	public List<Project> getSortedProjects(String name);

	public void createPasswordResetTokenForUser(User user, String token);

	public void saveUserPassword(User user, String password);

	public boolean buyMotivatorDonate(String userName, Motivator motivator);

	boolean addOfficeDonate(String username, int officeId);

	boolean addOfficeDonate(String username, Office office);

	public void changeBanned(String username);

}