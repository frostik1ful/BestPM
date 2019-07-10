package juke.service.interfases;

import java.util.List;

import juke.entities.Motivator;

public interface MotivatorService {
	
	public List<Motivator> getMotivators();
	
	public boolean save(Motivator motivator);
	public void saveChanged(Motivator motivator);
	public Motivator getById(Integer id);

	public void deleteMotivatorById(int motivatorId);

}
