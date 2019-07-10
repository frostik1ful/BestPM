package juke.controllers;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import juke.dto.UserInformation;
import juke.entities.User;
import juke.service.interfases.UserService;

@RestController
@Scope("request")
public class UserInformationController {

	private UserService userService;

	public UserInformationController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "user/information", method = RequestMethod.GET)
	public UserInformation getUserInformation() {
		User user = null;
		UserInformation userInformation = null;
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			userInformation = new UserInformation(user);
			return userInformation;
		} else {
			userInformation = new UserInformation();
			userInformation.setDonate(0);
			userInformation.setUserMoney(0);
			return userInformation;
		}
	}

}
