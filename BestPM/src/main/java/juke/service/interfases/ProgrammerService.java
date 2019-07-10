package juke.service.interfases;

import java.util.List;

import juke.entities.Programmer;

public interface ProgrammerService {

	public List<Programmer> getRandomProgrammers();
	
	public List<Programmer> getAllProgrammers();
	
	public boolean deleteProgrammerById(Integer programmerId);

	public boolean saveProgrammer(Programmer programmer);

	public Programmer getProgrammer(int programmerId);
	
	public void assignOnProject(int programmerId, int projectId);

	public int getCountProgrammers(String userName);


}
