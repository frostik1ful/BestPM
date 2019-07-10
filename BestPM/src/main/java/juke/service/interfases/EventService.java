package juke.service.interfases;

import juke.entities.Event;

public interface EventService {

	public boolean save(Event event);
	
	public void createEvent(String name, String type,String isHappy);
	
	public String releaseEvent();
	
	public String startEvent();
	
	public Event getById(Integer id);

	public String setDisease(Event event);

	public String setFinancial(Event event);
	
	public String setAccient(Event event);
	
	public void deleteEventById(int id);
	public void saveChanged(Event event);
}
