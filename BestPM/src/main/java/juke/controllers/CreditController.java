package juke.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import juke.entities.Credit;
import juke.entities.User;
import juke.repositories.UserRepository;
import juke.service.interfases.CreditService;
import juke.utils.interfaces.CreditFactory;

@Controller
@RequestMapping("credit")
public class CreditController {

	@Autowired
	private CreditService creditService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CreditFactory creditFactory;

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String credit(Model model, @RequestParam(name = "active", defaultValue = "1") Integer active) {
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		
		if (active == 1) {
			
			
			
			model.addAttribute("credits", creditFactory.getListCredits());
			model.addAttribute("creditsUser", user.getCreditList());
			model.addAttribute("activeFirst", "active");
		} else {
			model.addAttribute("credits", user.getCreditList());
			model.addAttribute("activeSecond", "active");
		}
		
		
		return "credit";

	}
	
	@RequestMapping(value = "/claim", method = RequestMethod.GET)
	public String claim(Model model, @RequestParam int amount, @RequestParam float rating,
			@RequestParam float ratingChange, @RequestParam int time, @RequestParam float percent, @RequestParam String bankName) {
		Credit credit = new Credit(amount, rating, ratingChange, time, percent, bankName);
		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		creditService.claimCredit(credit);
		model.addAttribute("creditTakens", user.getCreditList());
		return "redirect:/credit/new?active=1";

	}

	@RequestMapping(value = "/return", method = RequestMethod.GET)
	public String returnCredit(Model model, int id) {

		User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		creditService.returnCredit(id);
		model.addAttribute("creditTakens", user.getCreditList());
		return "redirect:/credit/new?active=0";

	}

}
