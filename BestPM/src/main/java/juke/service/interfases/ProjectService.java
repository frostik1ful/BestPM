package juke.service.interfases;

import java.util.List;
import java.util.Set;

import juke.entities.Project;

public interface ProjectService {
	boolean createProject(Project project);

	boolean deleteProjectById(int id);

	boolean saveChangesInProject(Project project, String type);

	Project getProjectByName(String name);

	Project getProjectById(int id);

	List<Project> getRandomProjects(int count);

	Set<Project> getAllProjects();

	boolean saveInProject(Project p);
}
