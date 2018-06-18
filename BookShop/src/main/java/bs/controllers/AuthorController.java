package bs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bs.models.Author;
import bs.services.interfaces.AuthorService;

@Controller
@RequestMapping("/author")
public class AuthorController {

	@Autowired
	AuthorService authorService;

	@RequestMapping(value = "/all")
	public ModelAndView all(ModelMap model) {
		model.addAttribute("authors", authorService.getAll());
		return new ModelAndView("authors", model);

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam String name) {
		Author author = new Author(name);
		authorService.save(author);
		return "redirect:/admin/authors";
	}

	@RequestMapping(value = "/edit")
	public String edit(Model model, @RequestParam int authorId) {
		Author author = authorService.getById(authorId);
		model.addAttribute("editAuthorId", authorId);
		model.addAttribute("authorName", author.getName());

		return "authors";
	}

	@RequestMapping(value = "/saveChanges")
	public String saveChanges(@RequestParam int authorId, @RequestParam String name) {
		Author author = authorService.getById(authorId);
		author.setName(name);
		authorService.save(author);
		return "redirect:/admin/authors";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam int authorId) {
		authorService.deleteById(authorId);
		return "redirect:/admin/authors";
	}
}
