package juke.utils.interfaces;

import java.util.List;

import juke.entities.User;
import juke.utils.classes.ProgressMessage;

public interface GameLogic {

	public List<ProgressMessage> generateDailyProgress(User user);

	public boolean isCanPlay();

}
