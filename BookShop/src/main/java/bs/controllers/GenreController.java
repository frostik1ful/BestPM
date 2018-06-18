package bs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bs.models.Genre;
import bs.services.interfaces.GenreService;

@Controller
@RequestMapping("/genre")
public class GenreController {

	@Autowired
	GenreService genreService;

	@RequestMapping(value = "/all")
	public ModelAndView all(ModelMap model) {
		model.addAttribute("genress", genreService.getAll());
		return new ModelAndView("genres", model);

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam String name) {
		Genre genre = new Genre(name);
		genreService.save(genre);
		return "redirect:/admin/genres";
	}

	@RequestMapping(value = "/edit")
	public String edit(Model model, @RequestParam int genreId) {
		Genre genre = genreService.getById(genreId);
		model.addAttribute("editGenreId", genreId);
		model.addAttribute("genreName", genre.getName());

		return "genres";
	}

	@RequestMapping(value = "/saveChanges")
	public String saveChanges(@RequestParam int genreId, @RequestParam String name) {
		Genre genre = genreService.getById(genreId);
		genre.setName(name);
		genreService.save(genre);
		return "redirect:/admin/genres";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam int genreId) {
		genreService.deleteById(genreId);
		return "redirect:/admin/genres";
	}
}
