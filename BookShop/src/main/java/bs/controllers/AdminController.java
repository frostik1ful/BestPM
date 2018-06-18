package bs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import bs.services.interfaces.AuthorService;
import bs.services.interfaces.BookService;
import bs.services.interfaces.GenreService;
import bs.services.interfaces.OrderService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private GenreService genreService;
	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/")
	public String admin(ModelMap model) {
		return "adminPage";
	}

	@RequestMapping(value = "/addBook")
	public ModelAndView addBook(ModelMap model) {
		model.addAttribute("newBook", 1);
		model.addAttribute("authors", authorService.getAll());
		model.addAttribute("genres", genreService.getAll());
		return new ModelAndView("books", model);
	}

	@RequestMapping(value = "/books")
	public ModelAndView books(ModelMap model) {
		model.addAttribute("adminBooks", bookService.getAll());
		return new ModelAndView("books", model);
	}

	@RequestMapping(value = "/orders")
	public ModelAndView orders(ModelMap model) {
		model.addAttribute("orders", orderService.getAll());
		return new ModelAndView("orders", model);
	}

	@RequestMapping(value = "/authors")
	public ModelAndView authors(ModelMap model) {
		model.addAttribute("authors", authorService.getAll());
		return new ModelAndView("authors", model);
	}

	@RequestMapping(value = "/addAuthor")
	public String addAuthor(ModelMap model) {
		return "authors";
	}

	@RequestMapping(value = "/genres")
	public ModelAndView genres(ModelMap model) {
		model.addAttribute("genres", genreService.getAll());
		return new ModelAndView("genres", model);
	}

	@RequestMapping(value = "/addGenre")
	public String addGenre(ModelMap model) {
		return "genres";
	}

}
