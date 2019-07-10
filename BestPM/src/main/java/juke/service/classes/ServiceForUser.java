package juke.service.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import juke.entities.Motivator;
import juke.entities.Office;
import juke.entities.PasswordResetToken;
import juke.entities.Programmer;
import juke.entities.Project;
import juke.entities.Role;
import juke.entities.User;
import juke.entities.UserMotivators;
import juke.repositories.OfficeRepository;
import juke.repositories.PasswordResetTokenRepository;
import juke.repositories.ProgrammerRepository;
import juke.repositories.ProjectRepository;
import juke.repositories.RoleRepository;
import juke.repositories.UserMotivatorsRepository;
import juke.repositories.UserRepository;
import juke.service.interfases.UserService;
import juke.utils.interfaces.ChooseGenerator;
import juke.utils.interfaces.OfficeGenerator;
import utils.MotivatorType;

@Service
public class ServiceForUser implements UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private ProjectRepository projectRepository;
	private BCryptPasswordEncoder BCryptPasswordEncoder;
	private OfficeRepository officeRepository;
	private ProgrammerRepository programmerRepository;
	private ChooseGenerator chooseGenerator;
	private OfficeGenerator officeGenerator;
	private UserMotivatorsRepository userMotivatorRepository;
	private PasswordResetTokenRepository passwordTokenRepository;

	@Override
	public void save(User user) {
		Optional<Office> optional = officeRepository.findByLevel(1);
		Office office = null;
		if (optional.isPresent()) {
			office = optional.get();
		} else {
			office = officeGenerator.generateOffice(1);
		}
		user.setPassword(BCryptPasswordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
		Role role = roleRepository.findOne(1L);
		if (role != null) {
			roles.add(role);
		} else {
			role = new Role();
			role.setName("USER");
			roles.add(roleRepository.save(role));
		}
		user.setRoles(roles);
		user.setOffice(office);
		office.getUsers().add(user);
		userRepository.save(user);
		officeRepository.save(office);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	// <Fixed by Tkachenko>
	@Override
	public void addProject(String username, int projectId) {
		if (username != null) {
			User user = userRepository.findByUsername(username);
			Project project = projectRepository.findOne(projectId);
			Set<Project> projects = user.getProjects();
			projects.add(project);
			user.setProjects(projects);
			project.setUser(user);
			userRepository.save(user);
			projectRepository.save(project);
		}
	}

	// </Fixed by Tkachenko>
	@Override
	public Set<Project> getProjects(String username) {
		User user = userRepository.findByUsername(username);
		Set<Project> projects = user.getProjects();
		return projects;
	}

	@Override
	public boolean addOffice(String username, Office office) {
		User user = userRepository.findByUsername(username);
		if (user.getBalance() >= office.getPrice()) {
			user.setBalance(user.getBalance() - office.getPrice());
			user.setOffice(office);
			office.getUsers().add(user);
			userRepository.saveAndFlush(user);
			officeRepository.save(office);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addOffice(String username, int officeId) {
		User user = userRepository.findByUsername(username);
		Optional<Office> optional = officeRepository.findById(officeId);
		Office office;
		if (optional.isPresent()) {
			office = optional.get();
			if (user.getBalance() >= office.getPrice()) {
				user.setBalance(user.getBalance() - office.getPrice());
				user.setOffice(office);
				office.getUsers().add(user);
				userRepository.saveAndFlush(user);
				officeRepository.save(office);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	@Override
	public boolean addOfficeDonate(String username, Office office) {
		User user = userRepository.findByUsername(username);
		if (user.getDonate() >= office.getPriceDonate()) {
			user.setDonate(user.getDonate() - office.getPriceDonate());
			user.setOffice(office);
			office.getUsers().add(user);
			userRepository.saveAndFlush(user);
			officeRepository.save(office);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean addOfficeDonate(String username, int officeId) {
		User user = userRepository.findByUsername(username);
		Optional<Office> optional = officeRepository.findById(officeId);
		Office office;
		if (optional.isPresent()) {
			office = optional.get();
			if (user.getDonate() >= office.getPriceDonate()) {
				user.setDonate(user.getDonate() - office.getPriceDonate());
				user.setOffice(office);
				office.getUsers().add(user);
				userRepository.saveAndFlush(user);
				officeRepository.save(office);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	@Override
	public boolean hireProgrammer(String username, Programmer programmer, int price) {
		if (!programmer.isHired()) {
			User user = userRepository.findByUsername(username);
			List<Programmer> programmers = user.getProgrammers();
			Office office = user.getOffice();
			if (office != null) {
				if (programmers.size() < office.getCountProgrammers()) {
					boolean choose = chooseGenerator.generateChooseProgrammer(price, programmer.getMoney(),
							programmer.getRating());
					if (choose) {
						programmers.add(programmer);
						for (UserMotivators motivator : user.getMotivators()) {
							if (motivator.getType() == MotivatorType.CONSTANT) {
								programmer.setMotivation(programmer.getMotivation() + motivator.getPower());
							} else {
								if (motivator.getTimeAction() > 0) {
									programmer.setMotivation(programmer.getMotivation() + motivator.getPower());
								}
							}
						}
						programmer.setUser(user);
						programmer.setHired(true);
						programmer.setMoney(price);
						userRepository.save(user);
						programmerRepository.saveAndFlush(programmer);
						return true;

					}
				}
			}
		}
		return false;

	}

	@Override
	public boolean hireProgrammer(String username, int programmerId, int price) {
		Programmer programmer = programmerRepository.findOne(programmerId);
		if (!programmer.isHired()) {
			User user = userRepository.findByUsername(username);
			List<Programmer> programmers = user.getProgrammers();
			Office office = user.getOffice();
			if (office != null) {
				if (programmers.size() < office.getCountProgrammers()) {
					boolean choose = chooseGenerator.generateChooseProgrammer(price, programmer.getMoney(),
							programmer.getRating());
					if (choose) {
						programmers.add(programmer);
						for (UserMotivators motivator : user.getMotivators()) {
							if (motivator.getType() == MotivatorType.CONSTANT) {
								programmer.setMotivation(programmer.getMotivation() + motivator.getPower());
							} else {
								if (motivator.getTimeAction() > 0) {
									programmer.setMotivation(programmer.getMotivation() + motivator.getPower());
								}
							}
						}
						programmer.setUser(user);
						programmer.setHired(true);
						programmer.setMoney(price);
						userRepository.save(user);
						programmerRepository.saveAndFlush(programmer);
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean buyMotivator(String userName, Motivator motivator) {
		User user = userRepository.findByUsername(userName);
		UserMotivators userMotivator = new UserMotivators(motivator);
		if (user.getBalance() < motivator.getPrice()) {
			return false;
		}
		float maxMotivation = 10f;
		float motivation = 0f;
		if (motivator != null) {
			for (Programmer programmer : user.getProgrammers()) {
				if (programmer.isCanMotivatedChanged()) {
					motivation = programmer.getMotivation() + motivator.getPower();
					if (motivation <= maxMotivation) {
						programmer.setMotivation(motivation);
						programmerRepository.saveAndFlush(programmer);
					} else {
						programmer.setMotivation(maxMotivation);
						programmerRepository.saveAndFlush(programmer);
					}
				}
			}
			userMotivator.setUser(user);
			user.getMotivators().add(userMotivator);
			user.setBalance(user.getBalance() - motivator.getPrice());
			userMotivatorRepository.save(userMotivator);
			userRepository.saveAndFlush(user);
		}
		return true;
	}

	@Override
	public boolean buyMotivatorDonate(String userName, Motivator motivator) {
		User user = userRepository.findByUsername(userName);
		UserMotivators userMotivator = new UserMotivators(motivator);
		if (user.getDonate() < motivator.getPriceDonate()) {
			return false;
		}
		float maxMotivation = 10f;
		float motivation = 0f;
		if (motivator != null) {
			for (Programmer programmer : user.getProgrammers()) {
				if (programmer.isCanMotivatedChanged()) {
					motivation = programmer.getMotivation() + motivator.getPower();
					if (motivation <= maxMotivation) {
						programmer.setMotivation(motivation);
						programmerRepository.saveAndFlush(programmer);
					} else {
						programmer.setMotivation(maxMotivation);
						programmerRepository.saveAndFlush(programmer);
					}
				}
			}
			userMotivator.setUser(user);
			user.getMotivators().add(userMotivator);
			user.setDonate(user.getDonate() - motivator.getPriceDonate());
			userMotivatorRepository.save(userMotivator);
			userRepository.saveAndFlush(user);
		}
		return true;
	}

	@Override
	public boolean fireProgrammer(int programmerId, String name) {
		User user = userRepository.findByUsername(name);
		Programmer programmer = programmerRepository.findOne(programmerId);
		int salary = programmer.getMoney() / 30 * programmer.getDayInWork();
		if (user.getBalance() < salary) {
			return false;
		} else {
			user.setBalance(user.getBalance() - salary);
			Random random = new Random();
			switch (programmer.getRating()) {
			case JUNIOR:
				programmer.setMoney(random.nextInt(300) + 500);
				break;
			case MIDDLE:
				programmer.setMoney(random.nextInt(800) + 1000);
				break;
			case SENIOR:
				programmer.setMoney(random.nextInt(800) + 2000);
				break;
			case TEAMLEAD:
				programmer.setMoney(random.nextInt(800) + 3000);
				break;
			default:
				programmer.setMoney(random.nextInt(100) + 400);
				break;
			}
			programmer.setHired(false);
			programmer.setUser(null);
			programmer.setProject(null);
			programmer.setDayInWork(0);
			programmerRepository.saveAndFlush(programmer);
			userRepository.saveAndFlush(user);
			return true;
		}
	}

	@Override
	public List<UserMotivators> getUserMotivators(String username, boolean isAtive) {
		User user = userRepository.findByUsername(username);
		return userMotivatorRepository.findByUserAndIsActive(user, isAtive);
	}

	@Override
	public List<Programmer> getUserProgrammers(String username) {
		User user = userRepository.findByUsername(username);
		return programmerRepository.findAllByUser(user);
	}

	@Override
	public List<Project> getSortedProjects(String username) {
		User user = userRepository.findByUsername(username);
		Set<Project> projects = user.getProjects();
		List<Project> sortedList = new ArrayList<Project>();
		sortedList.addAll(projects);
		Collections.sort(sortedList);
		return sortedList;
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(User user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordTokenRepository.save(myToken);
	}

	@Override
	public void saveUserPassword(User user, String password) {
		user.setPassword(BCryptPasswordEncoder.encode(password));
		userRepository.save(user);
	}

	@Override
	public void changeBanned(String username) {
		User user = userRepository.findByUsername(username);
		if (user.isBanned()) {
			user.setBanned(false);
		} else {
			user.setBanned(true);
		}
		userRepository.save(user);
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Autowired
	public void setRoleRepository(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Autowired
	public void setProjectService(ProjectRepository projectRepository) {
		this.projectRepository = projectRepository;
	}

	@Autowired
	public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		BCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Autowired
	public void setOfficeRepository(OfficeRepository officeRepository) {
		this.officeRepository = officeRepository;
	}

	@Autowired
	public void setProgrammerRepository(ProgrammerRepository programmerRepository) {
		this.programmerRepository = programmerRepository;
	}

	@Autowired
	public void setChooseGenerator(ChooseGenerator chooseGenerator) {
		this.chooseGenerator = chooseGenerator;
	}

	@Autowired
	public void setOfficeGenerator(OfficeGenerator officeGenerator) {
		this.officeGenerator = officeGenerator;
	}

	@Autowired
	public void setUserMotivatorRepository(UserMotivatorsRepository userMotivatorRepository) {
		this.userMotivatorRepository = userMotivatorRepository;
	}

	@Autowired
	public void setPasswordTokenRepository(PasswordResetTokenRepository passwordTokenRepository) {
		this.passwordTokenRepository = passwordTokenRepository;
	}

}