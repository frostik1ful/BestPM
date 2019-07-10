package juke.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import juke.entities.User;
import juke.service.interfases.OfficeService;
import juke.service.interfases.UserService;

@Controller()
@RequestMapping("office")
public class OfficeController {
	private UserService userService;
	private OfficeService officeService;

	@Autowired
	public OfficeController(UserService userService, OfficeService officeService) {
		this.userService = userService;
		this.officeService = officeService;
	}

	@RequestMapping(value = "/myOffice")
	public String getMyOffice(Model model, @RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "success", required = false) String success) {
		if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
			// { changed by Sergey Christensen
			// check if user is banned
			if (user.isBanned()) {
				return "jailPage";
			}
			// } changed by Sergey Christensen
			model.addAttribute("office", user.getOffice());
			if (error != null) {
				model.addAttribute("error", error);
			}
			if (success != null) {
				model.addAttribute("success", success);
			}
			return "office";
		} else {
			return "index";
		}

	}

	@RequestMapping(value = "/market")
	public String officeMarket(Model model) {
		model.addAttribute("offices", officeService.getAll());
		return "officeMarket";
	}

	@RequestMapping(value = "/monitor")
	public String monitor(Model model) {
		return "monitor";
	}

}
