package juke.utils.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import juke.entities.Credit;
import juke.entities.Office;
import juke.entities.Programmer;
import juke.entities.Project;
import juke.entities.User;
import juke.entities.UserMotivators;
import juke.repositories.CreditRepository;
import juke.repositories.EventRepository;
import juke.repositories.OfficeRepository;
import juke.repositories.ProgrammerRepository;
import juke.repositories.ProjectRepository;
import juke.repositories.UserMotivatorsRepository;
import juke.repositories.UserRepository;
import juke.service.classes.ServiceForCredits;
import juke.service.classes.ServiceForEvent;
import juke.service.interfases.CreditService;
import juke.service.interfases.EventService;
import juke.utils.interfaces.GameLogic;
import utils.MotivatorType;
import utils.Rating;
import utils.TypeOfPayment;

@Component
public class DailyProgressGenerator implements GameLogic {

	private final float MIN_MOTIVATION = 0.5f;
	private UserRepository userRepository;
	private OfficeRepository officeRepository;
	private ProjectRepository projectRepository;
	private UserMotivatorsRepository userMotivatorsRepository;
	private CreditRepository creditRepository;
	
	private EventRepository eventRepository;
	private ProgrammerRepository programmerRepository;
	private CreditService creditService;
	private EventService eventService;
	
	private ArrayList<Integer> idProjects = new ArrayList<>();
	private ArrayList<Integer> idMotivators = new ArrayList<>();
	private ArrayList<Integer> idCredit = new ArrayList<>();
	private boolean canPlay = true;
	
	private User user;
	private List<String> notice = new ArrayList<>();
	@Autowired
	public DailyProgressGenerator(UserRepository userRepository, OfficeRepository officeRepository,
			ProjectRepository projectRepository, UserMotivatorsRepository userMotivatorsRepository,
			CreditRepository creditRepository,EventRepository eventRepository,ProgrammerRepository programmerRepository) {
		this.userRepository = userRepository;
		this.officeRepository = officeRepository;
		this.projectRepository = projectRepository;
		this.userMotivatorsRepository = userMotivatorsRepository;
		this.creditRepository = creditRepository;
		this.eventRepository=eventRepository;
		this.programmerRepository=programmerRepository;
	}

	@Override
	public List<ProgressMessage> generateDailyProgress(User user) {
		this.user = user;
		notice.clear();
		
		List<ProgressMessage> messages = setDayliProgressProjects(user.getSortedProjects());// сначала это!!!!!
		setDailyProgressCredits();
		letEventStart();
		setDailyProgressProgrammers();
		if (this.user.getBalance() <= -10000) {
			gameOver();
		}
		userRepository.saveAndFlush(this.user);
		if (!canPlay) {
			for (Integer id : idProjects) {
				projectRepository.delete(id);
			}
			for (Integer id : idMotivators) {
				userMotivatorsRepository.delete(id);
			}
			for (Integer id : idCredit) {
				creditRepository.delete(id);
			}
			canPlay = true;
			idProjects.clear();
			idMotivators.clear();
			idCredit.clear();
		}
		ProgressMessage userMessage = new ProgressMessage();
		userMessage.setUserBalance(user.getBalance());
		userMessage.setUserDonate(user.getDonate());
		userMessage.setNotice(notice);
		messages.add(userMessage);
		return messages;
	}

	@Override
	public boolean isCanPlay() {
		return canPlay;
	}
	
	private void setDailyProgressProgrammers() {
		float minMotivation = MIN_MOTIVATION;
		int countCheck = 0;
		for (UserMotivators userMotivators : user.getMotivators()) {
			if (userMotivators.getType() == MotivatorType.TEMPORARY) {
				if (userMotivators.getTimeAction() > 0) {
					minMotivation += userMotivators.getPower();
					userMotivators.setTimeAction(userMotivators.getTimeAction() - 1);
					if(userMotivators.getTimeAction() ==0) {
						notice.add("Дествие мотиватора "+userMotivators.getName()+" закончилось");
					}
				}
				
			} else {
				minMotivation += userMotivators.getPower();
				
			}
		}
		
		for (Programmer programmer : user.getProgrammers()) {
			//int salary = (programmer.getMoney() / 30) * programmer.getDayInWork();
			int salary = programmer.getMoney();
			if (programmer.getDayInWork() < 30) {
					programmer.setDayInWork(programmer.getDayInWork() + 1);
				} else {
					programmer.setDayInWork(0);
					user.setBalance(user.getBalance() - salary);
					notice.add("Программисту "+programmer.getName()+" Выплачено "+salary+"$");
				}
				if (programmer.getProject() != null) {// Проверка работает ли программист
					programmer.setProductivity(programmer.getProductivity() + 0.01f);
					if (programmer.getMotivation() - 0.05 >= minMotivation) {
						programmer.setMotivation(programmer.getMotivation() - 0.05f);
					} else {
						programmer.setMotivation(minMotivation);
					}
					programmer.setExperience(programmer.getExperience() + 0.1);
					updateRating(programmer);
				}
			
		}
		
		
	}

	private List<ProgressMessage> setDayliProgressProjects(List<Project> projects) {
		List<ProgressMessage> messages = new ArrayList<>();
		for (Project p : projects) {
			int timeLeftPenalty = (int) (((p.getTime() * 1.2) - p.getTime()) * -1);
			if (p.getDifficultLeft() > 0 && p.getTimeLeft() > timeLeftPenalty) {
				for (Programmer programmer : p.getProgrammers()) {
					p.setDifficultLeft(
							p.getDifficultLeft() - programmer.getProductivity() * programmer.getMotivation());
					p.setTimeOutLeft((int) (p.getTime() * 0.2));
				}
				p.setTimeLeft(p.getTimeLeft() - 1);
				p.setTimeOutLeft(p.getTimeOutLeft() - 1);
				float onePercent = p.getDifficult() / 100;
				p.setProgressPercent((p.getDifficult() - p.getDifficultLeft()) / onePercent);
				if (p.getDifficultLeft() > 0 && p.getTimeLeft() > timeLeftPenalty) {
					if (p.getTimeLeft() <= 0) {
						messages.add(new ProgressMessage(p.getId(), p.getTimeLeft(), p.getDifficultLeft(),
								p.getProgressPercent(), user.getBalance(), (int) (p.getMoney() * 0.8)));
					} else {
						messages.add(new ProgressMessage(p.getId(), p.getTimeLeft(), p.getDifficultLeft(),
								p.getProgressPercent(), user.getBalance(), p.getMoney()));
					}
				}
			}
			if (p.getTypeOfPayment() == TypeOfPayment.FIXPRICE && p.getDifficultLeft() <= 0) {
				if (p.getTimeLeft() <= 0) {
					user.setBalance(user.getBalance() + (int) (p.getMoney() * 0.8));
					notice.add("Проект "+p.getName()+" сдан +"+(int) (p.getMoney()*0.8)+"$");
				} else {
					user.setBalance(user.getBalance() + p.getMoney());
					notice.add("Проект "+p.getName()+" сдан +"+p.getMoney()+"$");
				}
			} else if (p.getTypeOfPayment() == TypeOfPayment.TIMEMATERIAL) {
				float monthMoney = (p.getProgressPercent() / 100 * p.getMoney()) - (p.getMoney() - p.getMoneyLeft());
				if (p.getTimeLeft() <= 0) {
					monthMoney = (int) (monthMoney * 0.8);
				}
				if ((p.getTime() - Math.abs(p.getTimeLeft())) % 30 == 0) {
					user.setBalance(user.getBalance() + (int) monthMoney);
					p.setMoneyLeft(p.getMoneyLeft() - (int) monthMoney);
					notice.add("Месячная выплата за проект "+p.getName()+"  "+(int) monthMoney+" $");

				} else if (p.getTimeLeft() == 0) {
					int payDays = p.getTime() % 30;
					int lastMoney = (int) (monthMoney / 30 * payDays);
					user.setBalance(user.getBalance() + lastMoney);
					p.setMoneyLeft(p.getMoneyLeft() - lastMoney);
					notice.add("Выплата за проект "+p.getName()+" + "+lastMoney+" $");
					
				} else if (p.getTimeLeft() <= timeLeftPenalty) {
					int lastMoney = (int) (monthMoney / 30 * Math.abs(timeLeftPenalty));
					user.setBalance((long) (user.getBalance() + lastMoney * 0.8));
					p.setMoneyLeft(p.getMoneyLeft() - lastMoney);
					notice.add("Выплата за проект "+p.getName()+" + "+(long) (lastMoney*0.8)+" $");
				}
				if (p.getDifficultLeft() <= 0) {
					user.setBalance(user.getBalance() + p.getMoneyLeft());
					notice.add("Проект "+p.getName()+" сдан"+" + "+p.getMoneyLeft()+" $");
				}
			}
			if (p.getTimeLeft() <= timeLeftPenalty || p.getDifficultLeft() <= 0 || p.getTimeOutLeft() <= 0) {
				if(p.getTimeLeft() <= timeLeftPenalty) {
					notice.add("Проект "+p.getName()+ " просрочен и списан");
				}
				else if(p.getTimeOutLeft()<=0) {
					notice.add("Проект "+p.getName()+ " списан за простой");
				}
				p.setUser(null);
				p.setTimeLeft(p.getTime());
				p.setDifficultLeft(p.getDifficult());
				p.setMoneyLeft(p.getMoney());
				p.setTimeOutLeft((int) (p.getTime() * 0.2));
				p.setProgressPercent(0);
				for (Programmer programmer : p.getProgrammers()) {
					programmer.setProject(null);
				}
				
			}

		}
		
		return messages;
		
	}

	private void setDailyProgressCredits() {
		ServiceForCredits s = new ServiceForCredits(creditRepository, userRepository);
		notice.addAll(s.timeForCredit());
		
		
	}
	
	private void letEventStart() {
		ServiceForEvent e = new ServiceForEvent(eventRepository, programmerRepository, userRepository);
		String s = e.startEvent();
		if(s!=null) {
			notice.add(s);
		}
		e.releaseEvent();
		
	}

	private void gameOver() {
		this.canPlay = false;
		dissolveProgrammers();
		dissolveUserMotivators();
		dissolveProjects();
		dissolveCredits();
		user.setBalance(5000L);
		notice.add("gameOver");
		Optional<Office> isOffice = officeRepository.findByLevel(1);
		if (isOffice.isPresent()) {
			user.setOffice(isOffice.get());
		}
	}

	private void updateRating(Programmer programmer) {
		Random random = new Random();
		if (programmer.getRating() == Rating.TRAINEE && programmer.getExperience() > 500) {
			programmer.setRating(Rating.JUNIOR);
			programmer.setMoney(random.nextInt(300) + 600);
		}
		if (programmer.getRating() == Rating.JUNIOR && programmer.getExperience() > 1000) {
			programmer.setRating(Rating.MIDDLE);
			programmer.setMoney(random.nextInt(1000) + 1000);
		}
		if (programmer.getRating() == Rating.MIDDLE && programmer.getExperience() > 2000) {
			programmer.setRating(Rating.SENIOR);
			programmer.setMoney(random.nextInt(1500) + 2000);
		}
		if (programmer.getRating() == Rating.SENIOR && programmer.getExperience() > 4000) {
			programmer.setRating(Rating.TEAMLEAD);
			programmer.setMoney(random.nextInt(1000) + 3000);
		}
	}
	
	
	private void dissolveProgrammers() {
		Random random = new Random();
		for (Programmer programmer : user.getProgrammers()) {
			programmer.setUser(null);
			programmer.setProject(null);
			programmer.setDayInWork(0);
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
		}
	}

	private void dissolveUserMotivators() {
		for (UserMotivators motivator : user.getMotivators()) {
			idMotivators.add(motivator.getId());
			motivator.setUser(null);
		}
	}

	private void dissolveProjects() {
		for (Project project : user.getProjects()) {
			idProjects.add(project.getId());
			project.setUser(null);
		}
	}

	private void dissolveCredits() {
		for (Credit credit : user.getCreditList()) {
			idCredit.add(credit.getId());
			credit.setUser(null);
		}
	}

}
