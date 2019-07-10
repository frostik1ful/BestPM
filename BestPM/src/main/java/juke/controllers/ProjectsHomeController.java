package juke.controllers;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import juke.entities.Project;
import juke.service.interfases.ProjectService;

@Controller
@Scope("singleton")
@RequestMapping("/projects")
public class ProjectsHomeController {
	// private int timeBetweenUpdates = 1000 * 60 * 5;
	// private long updateIn = 0;
	// private long updateTime = 0;
	private int howMuchToShow = 10;
	private int updateWhenLessThan = 4;
	private List<Project> projectlist;
	@Autowired
	private ProjectService projectService;

	// Handles and retrieves all projects and show it in a JSP page
	@RequestMapping(value = "/home", method = { RequestMethod.GET })
	public String getProjects(Model model, String error, Integer id) {
		// initializing or updating projectlist
		if (projectlist == null || projectlist.size() < updateWhenLessThan) {
			projectlist = projectService.getRandomProjects(howMuchToShow);
		}
		// deleting project from current list if user got it, then redirecting to
		// addProject
		if (id != null) {
			Iterator<Project> i = projectlist.iterator();
			while (i.hasNext()) {
				Project p = i.next();
				if (p.getId() == id) {
					i.remove();
				}
			}
			return "redirect:/user/addProject?id=" + id;
		}

		model.addAttribute("error", error);
		model.addAttribute("projectsList", projectlist);

		// if (System.currentTimeMillis() >= updateTime) {
		// updateTime = System.currentTimeMillis() + timeBetweenUpdates;
		// }
		// updateIn = updateTime - System.currentTimeMillis();
		// Attach time before update to the Model
		// int minutesLeft = (int) Math.floor(updateIn / (60 * 1000));
		// int secondsLeft = (int) ((updateIn % (1000 * 60)) / 1000);
		// model.addAttribute("modelTimerMinutes", minutesLeft);
		// model.addAttribute("modelTimerSeconds", secondsLeft);
		// This will resolve to projectHome.jsp
		return "projectsHome";
	}
}
