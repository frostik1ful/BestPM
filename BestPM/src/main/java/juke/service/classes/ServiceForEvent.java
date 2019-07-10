package juke.service.classes;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import juke.entities.Event;
import juke.entities.Motivator;
import juke.entities.Programmer;
import juke.entities.User;
import juke.repositories.EventRepository;
import juke.repositories.ProgrammerRepository;
import juke.repositories.UserRepository;
import juke.service.interfases.EventService;
import utils.EventType;

@Service
public class ServiceForEvent implements EventService {

	private EventRepository eventRepository;
	private ProgrammerRepository programmerRepository;
	private UserRepository userRepository;

	private Random rand = new Random();

	@Autowired
	public ServiceForEvent(EventRepository eventRepository, ProgrammerRepository programmerRepository,
			UserRepository userRepository) {
		this.eventRepository = eventRepository;
		this.programmerRepository = programmerRepository;
		this.userRepository = userRepository;

	}

	@Override
	public boolean save(Event event) {
		if (eventRepository.findByName(event.getName()) != null) {
			return false;
		} else {
			eventRepository.save(event);
			return true;
		}
	}

	@Override
	public Event getById(Integer id) {

		return eventRepository.findOne(id);
	}

	@Override
	public void createEvent(String name, String type, String isHappy) {
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		Event event = new Event();
		boolean happy = false;
		if(isHappy.equals("true")) {happy = true;};
		
		switch (type) {
		case "DISEASE":
			event.setEventType(EventType.DISEASE);
			event.setName(name);
			event.setPowerInFloat(0);
			event.setPowerInInteger(0);
			event.setEventTime(rand.nextInt(14) + 1);
			event.setHappy(happy);
			break;
		case "ACCIDENT":
			event.setEventType(EventType.ACCIDENT);
			event.setName(name);
			event.setPowerInFloat(rand.nextFloat());
			event.setPowerInInteger(0);
			event.setEventTime(rand.nextInt(6) + 1);
			event.setHappy(happy);
			break;
		case "FINANCIAL":
			event.setEventType(EventType.FINANCIAL);
			event.setName(name);
			event.setPowerInFloat(0);
			event.setPowerInInteger(rand.nextInt((int) (user.getCreditRating() * 100)));
			event.setEventTime(0);
			event.setHappy(happy);
			break;
		default:
			break;
		}
		save(event);

	}

	@Override
	public String setDisease(Event event) {
		String message = null;
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Programmer> programmers = user.getProgrammers();
		for (Programmer programmer : programmers) {
			if (programmer.isCanMotivatedChanged() == true) {
				if (rand.nextInt(1000) > 950) {
					message = "Программист " + programmer.getName() + " заболел болезнью " + event.getName();
					programmer.setMotivation(programmer.getMotivation() * event.getPowerInFloat());
					programmer.setCanMotivatedChanged(false);
					programmerRepository.save(programmer);
					break;
				}
			}
		}
		return message;

	}

	@Override
	public String setAccient(Event event) {
		String message = null;
		String power;
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Programmer> programmers = user.getProgrammers();
		message = "Произошло " + event.getName();
		for (Programmer programmer : programmers) {
			if (programmer.isCanMotivatedChanged() == true) {
				if (event.getHappy().equals(true)) {
					programmer.setMotivation(programmer.getMotivation() * (1 + event.getPowerInFloat()));
				} else {
					programmer.setMotivation(programmer.getMotivation() * event.getPowerInFloat());
				}
				;
				programmer.setCanMotivatedChanged(false);
				programmerRepository.save(programmer);
			}
		}
		power = String.valueOf(event.getPowerInFloat());
		if(power.length()>4&&power.contains(".")) {
		power = power.substring(0, power.indexOf('.')+3);
		}
		if (event.getHappy()) {
			message += " мотивация программистов увеличилась  на " + power;
		} else {
			message += " мотивация программистов уменьшилась на " + power;
		}
		return message;

	}

	@Override
	public String setFinancial(Event event) {
		String message = null;
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		message = "Произошло " + event.getName();
		if (event.getHappy().equals(true)) {
			user.setBalance(user.getBalance() + event.getPowerInInteger());
			message += "! ваш счет увеличился на " + event.getPowerInInteger() + "$";
		} else {
			user.setBalance(user.getBalance() - event.getPowerInInteger());
			message += "! ваш счет уменьшелися на " + event.getPowerInInteger() + "$";
			
		}
		userRepository.save(user);
		return message;

	}

	@Override
	public String releaseEvent() {
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		List<Programmer> programmers = user.getProgrammers();
		for (Programmer programmer : programmers) {
			if (programmer.isCanMotivatedChanged() == false) {
				programmer.setDayInEvent(programmer.getDayInEvent() - 1);
				if (programmer.getDayInEvent() == 0) {
					programmer.setMotivation(1);
					programmer.setCanMotivatedChanged(true);
					programmer.setDayInEvent(-1);
				}

			}
		}
		return null;

	}

	@Override
	public String startEvent() {
		String message = null;
		if (rand.nextInt(1000) > 850) {
			List<Event> events = eventRepository.findAll();
			Collections.shuffle(events);
			if(events.size()>0) {
			Event event = events.get(rand.nextInt(events.size() - 1));
			switch (event.getEventType()) {
			case ACCIDENT:
				message = setAccient(event);
				break;
			case DISEASE:
				message = setDisease(event);
				break;
			case FINANCIAL:
				message = setFinancial(event);
				break;
			default:
				break;
			}
			}
			return message;
		} else {
			return null;
		}

	}

	@Override
	public void deleteEventById(int id) {
		eventRepository.delete(id);
		
	}
	
	@Override
	public void saveChanged(Event event) {
		eventRepository.saveAndFlush(event);
		// TODO Auto-generated method stub
		
	}
}
