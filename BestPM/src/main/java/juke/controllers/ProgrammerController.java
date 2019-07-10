package juke.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import juke.entities.Programmer;
import juke.entities.User;
import juke.service.interfases.ProgrammerService;
import juke.service.interfases.UserService;

@Controller
@RequestMapping("programmer")
public class ProgrammerController {

	private UserService userService;
	private ProgrammerService programmerService;

	@Autowired
	public ProgrammerController(ProgrammerService programmerService, UserService userService) {
		this.programmerService = programmerService;
		this.userService = userService;
	}

	@RequestMapping(value = "/new")
	public String programmer() {
		return "programmer";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam String name, @RequestParam double exp) {
		programmerService.saveProgrammer(new Programmer(name, exp));
		return "programmer";

	}

	@RequestMapping(value = "/market")
	public String getProgrammersPenetration(Model model) {
		model.addAttribute("programmers", programmerService.getRandomProgrammers());
		return "programmersMarket";
	}

	@RequestMapping(value = "/hire", params = { "programmerId" }, method = RequestMethod.POST)
	public String hire(Model model, int programmerId) {
		model.addAttribute("programmer", programmerService.getProgrammer(programmerId));
		return "programmersHire";
	}

	// <Tkachenko>
	@RequestMapping(value = "/hireCall", params = { "programmerId", "price" }, method = RequestMethod.POST)
	public String hireProgrammer(Model model, int programmerId, int price) {
		String message = null;
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(userName);
		if (userService.hireProgrammer(SecurityContextHolder.getContext().getAuthentication().getName(), programmerId,
				price)) {
			message = "Поздравляем, вы успешно наняли программиста.";
			model.addAttribute("message", message);
		} else {
			if (user.getProgrammers().size() == user.getOffice().getCountProgrammers()) {
				message = "Где я должен работать, если у вас нет свободного рабочего места! Купите новый офис, звоните!";
			} else {
				message = "За такую ЗП работать не собираюсь, попробуйте нанять другого!";
			}
			model.addAttribute("error", message);
		}
		return "success";
	}

	@RequestMapping(value = "/appoint", params = { "programmerId", "projectId" }, method = RequestMethod.POST)
	public String appointProgrammer(int programmerId, int projectId) {
		programmerService.assignOnProject(programmerId, projectId);
		return "redirect:/user/myProgrammers";
	}

	@RequestMapping(value = "/fire", params = { "programmerId" }, method = RequestMethod.POST)
	public RedirectView fireProgrammer(RedirectAttributes attributes, int programmerId) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.fireProgrammer(programmerId, userName)) {
			attributes.addAttribute("succsess", "Програмист уволен!");
		} else {
			attributes.addAttribute("error",
					"У вас недостаточно денег чтобы заплатить программисту, рекомендуем взять кредит!");
		}
		return new RedirectView("/user/myProgrammers");
	}

	@RequestMapping(value = "/all/programmers", method = RequestMethod.GET)
	public ModelAndView getAll(ModelMap model,@RequestParam(name = "information",required = false) String information) {
		model.addAttribute("programmers", programmerService.getAllProgrammers());
		if(information != null) {
			model.addAttribute("information", information);
		}
		return new ModelAndView("allProgrammers", model);
	}
	
	@RequestMapping(value = "/{id}/delete", params = {"programmerId"}, method = RequestMethod.POST)
	public ModelAndView deleteProgrammer(ModelMap model,Integer programmerId) {
		String information = null;
		if(programmerService.deleteProgrammerById(programmerId)) {
			information = "Программист успешно удален!";
		} else {
			information = "Программист нанят пользователем! Удаление отменено!";
		}
		model.addAttribute("information", information);
		return new ModelAndView("redirect:/programmer/all/programmers", model);
	}
	// </Tkachenko>
}
