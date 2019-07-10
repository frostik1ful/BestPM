package juke.service.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import juke.entities.Programmer;
import juke.entities.Project;
import juke.entities.User;
import juke.repositories.ProgrammerRepository;
import juke.repositories.ProjectRepository;
import juke.repositories.UserRepository;
import juke.service.interfases.ProgrammerService;
import juke.utils.interfaces.ProgrammerFactory;
import utils.Rating;

@Service
public class ServiceForProgrammer implements ProgrammerService {

	private Random random;
	private ProgrammerRepository programmerRepository;
	private ProjectRepository projectRepository;
	private ProgrammerFactory programmerFactory;
	private UserRepository userService;

	@Autowired
	public ServiceForProgrammer(ProgrammerRepository programmerRepository, ProjectRepository prohectRepository,
			ProgrammerFactory programmerFactory, UserRepository userService) {
		this.random = new Random();
		this.programmerRepository = programmerRepository;
		this.projectRepository = prohectRepository;
		this.programmerFactory = programmerFactory;
		this.userService = userService;
	}

	@Override
	public List<Programmer> getRandomProgrammers() {
		ArrayList<Programmer> listProgrammersReturn = new ArrayList<>(10);
		int length = programmerRepository.countByIsHiredFalse();
		if (length < 50) {
			fillListProgrammers(listProgrammersReturn);
		} else {
			int countTeamLead = programmerRepository.countByRatingAndIsHiredFalse(Rating.TEAMLEAD);
			int countSenior = programmerRepository.countByRatingAndIsHiredFalse(Rating.SENIOR);
			int countMidle = programmerRepository.countByRatingAndIsHiredFalse(Rating.MIDDLE);
			int countJunior = programmerRepository.countByRatingAndIsHiredFalse(Rating.JUNIOR);
			if (countTeamLead < 6) {
				listProgrammersReturn.add(programmerFactory.generateTeamLead());
			} else {
				listProgrammersReturn.add(getRandomProgrammerByRating(Rating.TEAMLEAD));
			}
			if (countSenior < 10) {
				listProgrammersReturn.add(programmerFactory.generateSenior());
			} else {
				listProgrammersReturn.add(getRandomProgrammerByRating(Rating.SENIOR));
			}
			if (countMidle < 15) {
				listProgrammersReturn.add(programmerFactory.generateMiddle());
				listProgrammersReturn.add(programmerFactory.generateMiddle());
			} else {
				fillRandomProgrammersByRating(Rating.MIDDLE, listProgrammersReturn);
			}
			if (countJunior < 20) {
				for (int i = 0; i < 6; i++) {
					listProgrammersReturn.add(programmerFactory.generateJunior());
				}
			} else {
				fillRandomProgrammersByRating(Rating.JUNIOR, listProgrammersReturn);
			}

		}
		return listProgrammersReturn;
	}

	@Override
	public int getCountProgrammers(String userName) {
		User user = userService.findByUsername(userName);
		return programmerRepository.countByUser(user);
	}

	@Override
	public boolean saveProgrammer(Programmer programmer) {
		Programmer programmerSave = programmerRepository.save(programmer);
		if (programmerSave != null) {
			return true;
		}
		return false;
	}

	@Override
	public Programmer getProgrammer(int programmerId) {
		Programmer programmer = programmerRepository.findOne(programmerId);
		if (programmer != null) {
			return programmer;
		} else {
			return null;
		}

	}

	@Override
	public void assignOnProject(int programmerId, int projectId) {
		Programmer programmer = programmerRepository.findOne(programmerId);
		Project project = projectRepository.findOne(projectId);
		programmer.setProject(project);
		programmerRepository.saveAndFlush(programmer);

	}

	@Override
	public List<Programmer> getAllProgrammers() {
		return programmerRepository.findAllByOrderById();
	}

	@Override
	public boolean deleteProgrammerById(Integer programmerId) {
		Programmer programmer = programmerRepository.findOne(programmerId);
		if (programmer.getUser() == null) {
			if (programmer.getProject() == null) {
				programmerRepository.delete(programmerId);
			} else {
				programmer.setProject(null);
				programmerRepository.saveAndFlush(programmer);
				programmerRepository.delete(programmerId);
			}
			return true;
		} else {
			return false;
		}
	}

	private void fillListProgrammers(List<Programmer> programmers) {
		for (int i = 0; i < 4; i++) {
			switch (i) {
			case 0:
				programmers.add(programmerFactory.generateTeamLead());
				break;
			case 1:
				programmers.add(programmerFactory.generateSenior());
				break;
			case 2:
				programmers.add(programmerFactory.generateMiddle());
				programmers.add(programmerFactory.generateMiddle());
				break;
			case 3:
				for (int j = 0; j < 6; j++) {
					programmers.add(programmerFactory.generateJunior());
				}
				break;
			}
		}
	}

	private Programmer getRandomProgrammerByRating(Rating rating) {
		Programmer programmer = null;
		ArrayList<Programmer> programmers = new ArrayList<>(
				programmerRepository.findAllByRatingAndIsHiredFalse(rating));
		Collections.shuffle(programmers);
		programmer = programmers.get(random.nextInt(programmers.size() - 1));
		return programmer;
	}

	private void fillRandomProgrammersByRating(Rating rating, List<Programmer> programmersIn) {
		ArrayList<Programmer> programmers = new ArrayList<>(
				programmerRepository.findAllByRatingAndIsHiredFalse(rating));
		Collections.shuffle(programmers);
		switch (rating) {
		case MIDDLE:
			for (int i = 0; i < 2; i++) {
				programmersIn.add(programmers.get(i));
			}
			break;
		case JUNIOR:
			for (int i = 0; i < 6; i++) {
				programmersIn.add(programmers.get(i));
			}
			break;
		default:
			break;
		}

	}

}
