package juke.controllers;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import juke.entities.Project;
import juke.service.interfases.ProjectService;
import utils.TypeOfPayment;

@Controller
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	private Set<TypeOfPayment> payTypes = EnumSet.allOf(TypeOfPayment.class);

	@RequestMapping(value = "/all")
	public ModelAndView home(ModelMap model, @RequestParam(name = "information", required = false) String information) {
		model.addAttribute("projects", projectService.getAllProjects());
		if (information != null) {
			model.addAttribute("information", information);
		}
		return new ModelAndView("project", model);
	}

	@RequestMapping(value = "/new")
	public String project(Model model) {
		model.addAttribute("payTypes", payTypes);
		return "project";
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap model, @RequestParam int projectId) {
		Project project = projectService.getProjectById(projectId);
		String information = null;
		if (project.getUser() == null) {
			information = "Проект " + project.getName() + " успешно удален!";
			projectService.deleteProjectById(projectId);
		} else {
			information = "Проект " + project.getName() + " находится в разработке пользователя " + project.getUser().getUsername()
					+ ". Удаление отменено!";
		}
		model.addAttribute("information", information);
		return new ModelAndView("redirect:/project/all", model);
	}

	@RequestMapping(value = "/edit")
	public String edit(Model model, @RequestParam int projectId) {
		Project p = projectService.getProjectById(projectId);
		model.addAttribute("editProject", projectId);
		model.addAttribute("payTypes", payTypes);
		model.addAttribute("selectedPayType", p.getTypeOfPayment());
		model.addAttribute("name", p.getName());
		model.addAttribute("money", p.getMoney());
		model.addAttribute("time", p.getTime());
		model.addAttribute("difficult", p.getDifficult());
		return "project";
	}

	@RequestMapping(value = "/saveChanges")
	public ModelAndView saveChanges(ModelMap model, @RequestParam int projectId, @RequestParam String name,
			@RequestParam int time, @RequestParam int money, @RequestParam float difficult,
			@RequestParam TypeOfPayment type) {
		Project edited = projectService.getProjectById(projectId);
		String information;
		if (edited.getUser() == null) {
			edited.setName(name);
			edited.setTime(time);
			edited.setTimeLeft(time);
			edited.setMoney(money);
			edited.setMoneyLeft(money);
			edited.setDifficult(difficult);
			edited.setDifficultLeft(difficult);
			edited.setTypeOfPayment(type);
			projectService.saveInProject(edited);
			information = "Проект " + edited.getName() + " успешно изменен!";
		} else {
			information = "Проект " + edited.getName() + " находится в разработке пользователя " + edited.getUser().getUsername()
					+ ". Изменения отменены!";
		}
		model.addAttribute("information", information);
		return new ModelAndView("redirect:/project/all", model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam String name, @RequestParam int time, @RequestParam int money,
			@RequestParam float difficult, @RequestParam TypeOfPayment type) {
		Project proj = new Project(name, time, money, difficult, type);
		projectService.createProject(proj);
		return "redirect:/project/all";
	}

}
