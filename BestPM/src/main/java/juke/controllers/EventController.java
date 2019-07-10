package juke.controllers;



import java.util.EnumSet;
import java.util.Set;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import juke.entities.Event;
import juke.entities.Motivator;
import juke.repositories.EventRepository;
import juke.service.interfases.EventService;
import utils.EventType;
import utils.MotivatorType;

@Controller
@RequestMapping("event")
@Scope("request")
public class EventController {

	private EventService eventService;
	private EventRepository eventRepository;
	private Set<EventType> EventTypes = EnumSet.allOf(EventType.class);
	

	@Autowired
	public EventController(EventService eventService, EventRepository eventRepository) {
		this.eventService = eventService;
		this.eventRepository = eventRepository;
	}

	@RequestMapping(value = "/new")
	public String events(Model model) {
		model.addAttribute("events", eventRepository.findAll());
		return "event";

	}
	@RequestMapping(value = "/addNewEvent")
	public String newEvent(Model model, String name, String type, @RequestParam(name = "isHappy", defaultValue = "false") String isHappy) {
		
		eventService.createEvent(name, type, isHappy);		
		model.addAttribute("events", eventRepository.findAll());
		return "redirect:/event/new";

	}
	
	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap model, @RequestParam int eventsId) {
		eventService.deleteEventById(eventsId);
		return new ModelAndView("redirect:/event/new", model);
	}
	
	@RequestMapping(value = "/edit")
	public String edit(Model model, @RequestParam int eventsId) {
		Event e = eventService.getById(eventsId);
		model.addAttribute("editEvent", eventsId);
		model.addAttribute("EventTypes", EventTypes);
		model.addAttribute("selectedEventType", e.getEventType());
		model.addAttribute("name", e.getName());
		model.addAttribute("eventPowerF", e.getPowerInFloat());
		model.addAttribute("eventPowerI", e.getPowerInInteger());
		model.addAttribute("eventIsHappy", e.getHappy());
		model.addAttribute("eventTime", e.getEventTime());
		return "event";
	}
	
	@RequestMapping(value = "/saveChanges")
	public ModelAndView saveChanges(ModelMap model,@RequestParam int eventId, @RequestParam String name,
			@RequestParam float eventPowerF,@RequestParam int eventPowerI,@RequestParam int eventTime,@RequestParam(defaultValue = "false") Boolean isHappy,@RequestParam EventType type) {
		System.out.println(eventId+" "+name+" "+eventPowerF+" "+eventPowerI+" "+ eventTime+" "+isHappy+" "+type);
		Event edited = eventService.getById(eventId);
		edited.setName(name);
		edited.setPowerInFloat(eventPowerF);
		edited.setPowerInInteger(eventPowerI);
		edited.setEventTime(eventTime);
		edited.setHappy(isHappy);
		edited.setEventType(type);
		
		eventService.saveChanged(edited);
		return new ModelAndView("redirect:/event/new", model);
	}
	




}
