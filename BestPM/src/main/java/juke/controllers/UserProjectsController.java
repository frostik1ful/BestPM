package juke.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import juke.entities.Project;
import juke.service.interfases.ProgrammerService;
import juke.service.interfases.ProjectService;
import juke.service.interfases.SecurityService;
import juke.service.interfases.UserService;

@Controller
@RequestMapping("userProjects")
public class UserProjectsController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;
	@Autowired
	private ProgrammerService programmerService;
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/", method = { RequestMethod.GET })
	public String userProjects(Model model) {
		model.addAttribute("projectsSet",
				userService.getSortedProjects(SecurityContextHolder.getContext().getAuthentication().getName()));
		model.addAttribute("userBalance", userService
				.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getBalance());
		return "userProjects";
	}

	@RequestMapping(value = "/projectInfo", method = RequestMethod.GET)
	public String projectInfo(Model model, @RequestParam Long projectId) {
		Project selected = null;
		for (Project p : userService.getProjects(SecurityContextHolder.getContext().getAuthentication().getName())) {
			if (p.getId() == projectId) {
				selected = p;
				break;
			}
		}
		model.addAttribute("selectedProject", selected);
		return "userProjects";
	}

	/*
	 * @RequestMapping(value = "/addProgrammer", method = RequestMethod.GET) public
	 * String addProgrammer(@RequestParam Integer projectId,@RequestParam Integer
	 * programmerId) {
	 * 
	 * Project project=projectService.getProjectById(projectId);
	 * 
	 * System.out.println("+++++++++++++++++++++++++++++ "+project.getName() );
	 * Programmer programmer = programmerService.getProgrammer(programmerId);
	 * System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^ prjectName "+project.getName(
	 * )+" projrammerName "+programmer.getName() );
	 * project.getProgrammers().add(programmer);
	 * projectService.saveInProject(project); programmer.setProject(project);
	 * programmer.setHired(true); programmerService.saveProgrammer(programmer);
	 * return "redirect:/userProjects/"; }
	 */

	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
	public String addProject(@RequestParam Integer projectId) {
		/* System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^" + projectId); */
		Project p = projectService.getProjectById(projectId);
		p.setUser(userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
		projectService.saveInProject(p);
		return "redirect:/userProjects/";
	}
}
