package juke.controllers;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import juke.entities.Motivator;
import juke.entities.Project;
import juke.service.interfases.MotivatorService;
import utils.MotivatorType;
import utils.TypeOfPayment;

@Controller
@RequestMapping("/motivator")
@Scope("request")
public class MotivatorController {

	private Set<MotivatorType> MotivatorTypes = EnumSet.allOf(MotivatorType.class);
	private MotivatorService motivatorService;

	@Autowired
	public MotivatorController(MotivatorService motivatorService) {
		this.motivatorService = motivatorService;
	}

	@ModelAttribute
	public void addAttributes(Model model) {
		model.addAttribute("motivator", new Motivator());
		model.addAttribute("typeMotivators", MotivatorTypes);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute("motivator") Motivator motivator, ModelMap attributes) {
		if (motivatorService.save(motivator)) {
			attributes.addAttribute("succsess", "Успешно сохранен.");
		} else {
			attributes.addAttribute("error", "Мотиватор с таким именем существует");
		}
		return new ModelAndView("redirect:/motivator/new", attributes);
	}

	@RequestMapping(value = "/new")
	public String defaultMotivator(Model model, @RequestParam(name = "succsess", required = false) String succsess,
			@RequestParam(name = "error", required = false) String error) {
		if (error != null) {
			model.addAttribute("error", error);
		}
		if (succsess != null) {
			model.addAttribute("succsess", succsess);
		}
		return "addMotivator";
	}

	@RequestMapping(value = "/market")
	public String marketMotivators(Model model, @RequestParam(name = "error", required = false) String error) {
		model.addAttribute("motivators", motivatorService.getMotivators());
		model.addAttribute("type", MotivatorType.TEMPORARY);
		if (error != null) {
			model.addAttribute("error", error);
		}
		return "marketMotivators";
	}
	
	@RequestMapping(value = "/all")
	public ModelAndView getMotivators(ModelMap model ) {
		model.addAttribute("motivators", motivatorService.getMotivators());
		return new ModelAndView("motivator", model);
	}
	
	@RequestMapping(value = "/delete")
	public ModelAndView delete(ModelMap model, @RequestParam int motivatorId) {
		motivatorService.deleteMotivatorById(motivatorId);
		return new ModelAndView("redirect:/motivator/all", model);
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(Model model, @RequestParam int motivatorId) {
		Motivator m = motivatorService.getById(motivatorId);
		model.addAttribute("editMotivator", motivatorId);
		model.addAttribute("MotivatorTypes", MotivatorTypes);
		model.addAttribute("selectedMotivatorType", m.getType());
		model.addAttribute("name", m.getName());
		model.addAttribute("motivatorPower", m.getPower());
		model.addAttribute("motivatorPrice", m.getPrice());
		model.addAttribute("motivatorDonatePrice", m.getPriceDonate());
		model.addAttribute("timeOfAction", m.getTimeOfAction());
		model.addAttribute("discription", m.getDiscription());
		return "motivator";
	}
	
	@RequestMapping(value = "/saveChanges")
	public ModelAndView saveChanges(ModelMap model, @RequestParam int motivatorId, @RequestParam String name,
			@RequestParam float power,@RequestParam int price,@RequestParam int donatePrice,@RequestParam String discription,@RequestParam int timeOfAction,@RequestParam MotivatorType type) {
		Motivator edited = motivatorService.getById(motivatorId);
		edited.setName(name);
		edited.setPrice(price);
		edited.setPower(power);
		edited.setPriceDonate(donatePrice);
		edited.setDiscription(discription);
		if(type==MotivatorType.TEMPORARY) {
			edited.setTimeOfAction(timeOfAction);
				edited.setType(MotivatorType.TEMPORARY);
		}
		else {
			edited.setTimeOfAction(0);
			edited.setType(MotivatorType.CONSTANT);
		}
		motivatorService.saveChanged(edited);
		return new ModelAndView("redirect:/motivator/all", model);
	}

}
