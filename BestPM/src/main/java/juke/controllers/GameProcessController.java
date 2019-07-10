package juke.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import juke.entities.User;
import juke.service.interfases.UserService;
import juke.utils.classes.NoticeMessage;
import juke.utils.classes.ProgressMessage;
import juke.utils.interfaces.GameLogic;

@RestController
 @Scope("session") 
public class GameProcessController {

	private UserService userService;
	private GameLogic gameLogic;
	private long afterSeconds = 60;
	private NoticeMessage noticeMessage = new NoticeMessage();
	@Autowired
	public GameProcessController(UserService userService, GameLogic gameLogic) {
		this.userService = userService;
		this.gameLogic = gameLogic;
	}
	@RequestMapping("/userProjects/hideNotice")
	@ResponseBody
	public void hideNotice() {
		this.noticeMessage.setShowNotify(false);

	}
	
	@RequestMapping("/userProjects/showNotice")
	@ResponseBody
	public void showNotice() {
		this.noticeMessage.setShowNotify(true);

	}
	
	@RequestMapping("/userProjects/getNotice")
	@ResponseBody
	public NoticeMessage getNotice() {
		return noticeMessage;

	}
	@RequestMapping("/userProjects/getInfo")
	@ResponseBody
	public List<ProgressMessage> getInfo() {
		User currentUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		// DailyProgressGenerator progressGenerator = new
		// DailyProgressGenerator(userRepository, programmerRepository);
		List<ProgressMessage> messages = gameLogic.generateDailyProgress(currentUser);
		this.noticeMessage.getNotice().addAll(messages.get(messages.size()-1).getNotice());
		//this.notice.addAll(messages.get(messages.size()-1).getNotice());
		// userRepository.save(currentUser);
		return messages;

	}

	@RequestMapping("/userProjects/getSeconds")
	@ResponseBody
	public ProgressMessage getSeconds() {
		User currentUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		ProgressMessage message = new ProgressMessage();
		message.setSecondsLeft(afterSeconds);
		message.setUserDonate(currentUser.getDonate());
		message.setUserBalance(currentUser.getBalance());
		return message;

	}

	@RequestMapping("/userProjects/setSeconds")
	@ResponseBody
	public ProgressMessage setSeconds() {
		if (afterSeconds == 0) {
			afterSeconds = 60;
		}
		afterSeconds--;
		User currentUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		ProgressMessage message = new ProgressMessage();
		message.setSecondsLeft(afterSeconds);
		message.setUserDonate(currentUser.getDonate());
		message.setUserBalance(currentUser.getBalance());
		return message;

	}

}
