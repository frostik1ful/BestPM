package juke.utils.interfaces;

import juke.entities.Programmer;

public interface ProgrammerFactory {
	
	public Programmer generateTeamLead();
	public Programmer generateSenior();
	public Programmer generateMiddle();
	public Programmer generateJunior();
	public Programmer generateTrainer();

}
