package juke.service.classes;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import juke.entities.Project;
import juke.repositories.ProjectRepository;
import juke.service.interfases.ProjectService;
import juke.utils.interfaces.ProjectFactory;

@Service
public class ServiceForProject implements ProjectService {
	private ProjectRepository projectRepository;
	private ProjectFactory projectFactory;

	@Autowired
	public ServiceForProject(ProjectRepository projectRepository, ProjectFactory projectFactory) {
		this.projectRepository = projectRepository;
		this.projectFactory = projectFactory;
	}

	@Override
	public boolean createProject(Project project) {
		projectRepository.save(project);
		return true;
	}

	@Override
	public Set<Project> getAllProjects() {
		Set<Project> projects = new HashSet<>(projectRepository.findAll());
		return projects;
	}

	@Override
	public boolean deleteProjectById(int id) {
		projectRepository.delete(id);
		return false;
	}

	@Override
	public Project getProjectByName(String name) {
		Optional<Project> optionalProject = projectRepository.findByName(name);
		return optionalProject.get();
	}

	@Override
	public boolean saveInProject(Project p) {
		projectRepository.saveAndFlush(p);
		return false;
	}

	@Override
	public boolean saveChangesInProject(Project p, String type) {
		/*
		 * projectRepository.saveChanges(p.getName(), p.getMoney(),p.getDate(),
		 * p.getDifficult(), type, p.getId());
		 */
		return false;
	}

	@Override
	public Project getProjectById(int id) {
		Project project = projectRepository.getOne(id);
		return project;
	}

	// { Fixed by Sergey Christensen
	// @Override

	// getting projects from projectRepository by rules:
	// easyProjectMaxDifficult = **F;
	// mediumProjectMaxDifficult = **F;
	// hardProjectMaxDifficult = **F;
	// **% easy projects, **% hard projects, other - medium projects
	// generating different types of projects if necessary
	@Override
	public List<Project> getRandomProjects(int count) {
		// setting difficult ranges for project types
		float easyProjectMinDifficult = 400F;
		float easyProjectMaxDifficult = 800F;
		float mediumProjectMaxDifficult = 4000F;
		float hardProjectMaxDifficult = 15000F;
		int percentOfEasyProjects = 30;
		int percentOfHardProjects = 30;

		// getting List<Project> of different project types
		List<Project> easyProjects = projectRepository.findByUserIsNullAndDifficultGreaterThanAndDifficultLessThanEqual(
				easyProjectMinDifficult, easyProjectMaxDifficult);
		List<Project> mediumProjects = projectRepository
				.findByUserIsNullAndDifficultGreaterThanAndDifficultLessThanEqual(easyProjectMaxDifficult,
						mediumProjectMaxDifficult);
		List<Project> hardProjects = projectRepository.findByUserIsNullAndDifficultGreaterThanAndDifficultLessThanEqual(
				mediumProjectMaxDifficult, hardProjectMaxDifficult);

		Collections.shuffle(easyProjects);
		Collections.shuffle(mediumProjects);
		Collections.shuffle(hardProjects);
		// generating projects if in list are less then 2*count projects

		if (easyProjects.size() < 2 * count) {
			for (int i = 0; i < count; i++) {
				projectFactory.generateProject((float) Math.floor((easyProjectMinDifficult
						+ Math.random() * (easyProjectMaxDifficult - easyProjectMinDifficult))));
			}
		}
		if (mediumProjects.size() < 2 * count) {
			for (int i = 0; i < count; i++) {
				projectFactory.generateProject((float) Math.floor((easyProjectMaxDifficult
						+ Math.random() * (mediumProjectMaxDifficult - easyProjectMaxDifficult))));
			}
		}
		if (hardProjects.size() < 2 * count) {
			for (int i = 0; i < count; i++) {
				projectFactory.generateProject((float) Math.floor((mediumProjectMaxDifficult
						+ Math.random() * (hardProjectMaxDifficult - mediumProjectMaxDifficult))));
			}
		}
		// getting projects to result list
		int easyProjectsCount = count * percentOfEasyProjects / 100;
		int hardProjectsCount = count * percentOfHardProjects / 100;
		int mediumProjectsCount = count - easyProjectsCount - hardProjectsCount;
		List<Project> result = easyProjects.subList(0, easyProjectsCount);
		result.addAll(mediumProjects.subList(0, mediumProjectsCount));
		result.addAll(hardProjects.subList(0, hardProjectsCount));

		return result;
	}
	// } Fixed by Sergey Christensen

}
