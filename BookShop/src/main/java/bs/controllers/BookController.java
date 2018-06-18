package bs.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bs.models.Author;
import bs.models.Book;
import bs.models.Genre;
import bs.services.interfaces.AuthorService;
import bs.services.interfaces.BookService;
import bs.services.interfaces.GenreService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private AuthorService authorService;
	@Autowired
	private GenreService genreService;

	@RequestMapping(value = "/")
	public ModelAndView all(ModelMap model) {
		model.addAttribute("books", bookService.getAll());
		model.addAttribute("authors", authorService.getAll());
		model.addAttribute("genres", genreService.getAll());
		return new ModelAndView("books", model);

	}

	@RequestMapping(value = "/authorSort")
	public ModelAndView authorSort(ModelMap model, @RequestParam int authorId) {
		model.addAttribute("authors", authorService.getAll());
		model.addAttribute("genres", genreService.getAll());
		List<Book> books = bookService.getAll();
		List<Book> authorBooks = new ArrayList<>();
		for (Book book : books) {
			boolean bookHasAuthor = false;
			for (Author author : book.getAuthors()) {
				if (author.getId() == authorId) {
					bookHasAuthor = true;
					break;
				}
			}
			if (bookHasAuthor) {
				authorBooks.add(book);
			}
		}
		model.addAttribute("authorBooks", authorBooks);
		return new ModelAndView("books", model);

	}

	@RequestMapping(value = "/genreSort")
	public ModelAndView genreSort(ModelMap model, @RequestParam int genreId) {
		model.addAttribute("authors", authorService.getAll());
		model.addAttribute("genres", genreService.getAll());
		List<Book> books = bookService.getAll();
		List<Book> genreBooks = new ArrayList<>();
		for (Book book : books) {
			boolean bookHasGenre = false;
			for (Genre genre : book.getGenres()) {
				if (genre.getId() == genreId) {
					bookHasGenre = true;
					break;
				}
			}
			if (bookHasGenre) {
				genreBooks.add(book);
			}
		}
		model.addAttribute("genreBooks", genreBooks);
		return new ModelAndView("books", model);

	}

	@RequestMapping(value = "/addBook", method = RequestMethod.POST)
	public String add(@RequestParam String name, @RequestParam String description, @RequestParam int price,
			@RequestParam("authorsId") int[] authorsId, @RequestParam("genresId") int[] genresId) {
		Set<Author> authors = new HashSet<>();
		Set<Genre> genres = new HashSet<>();
		for (int authorId : authorsId) {
			if (authorId > 0)
				authors.add(authorService.getById(authorId));
		}

		for (int genreId : genresId) {
			if (genreId > 0)
				genres.add(genreService.getById(genreId));
		}
		Book newBook = new Book(name, description, price, authors, genres);
		bookService.save(newBook);
		return "redirect:/admin/books";
	}

	@RequestMapping(value = "/info", method = RequestMethod.POST)
	public ModelAndView showInfo(ModelMap model, @RequestParam int bookId) {
		model.addAttribute("book", bookService.getById(bookId));
		return new ModelAndView("book/info", model);
	}

	@RequestMapping(value = "/editBook")
	public String edit(Model model, @RequestParam int editBookId) {
		Book book = bookService.getById(editBookId);
		model.addAttribute("editBookId", editBookId);
		model.addAttribute("name", book.getName());
		model.addAttribute("description", book.getDescription());
		model.addAttribute("price", book.getPrice());
		List<Author> authors = authorService.getAll();
		Set<Author> bookAuthors = book.getAuthors();
		authors.removeAll(bookAuthors);
		List<Genre> genres = genreService.getAll();
		Set<Genre> bookGenres = book.getGenres();
		genres.removeAll(bookGenres);
		model.addAttribute("bookAuthors", bookAuthors);
		model.addAttribute("authors", authors);
		model.addAttribute("bookGenres", bookGenres);
		model.addAttribute("genres", genres);

		// model.addAttribute("authors", authorService.getAll());
		// model.addAttribute("genres", genreService.getAll());
		// model.addAttribute("bookAuthors", book.getAuthors());
		// model.addAttribute("bookGenres", book.getGenres());
		return "books";
	}

	@RequestMapping(value = "/saveChangesBook")
	public String saveChanges(@RequestParam int editBookId, @RequestParam String name, @RequestParam String description,
			@RequestParam int price, @RequestParam("authorsId") int[] authorsId,
			@RequestParam("genresId") int[] genresId) {
		Book book = bookService.getById(editBookId);
		Set<Author> authors = new HashSet<>();
		Set<Genre> genres = new HashSet<>();
		for (int authorId : authorsId) {
			if (authorId > 0)
				authors.add(authorService.getById(authorId));
		}

		for (int genreId : genresId) {
			if (genreId > 0)
				genres.add(genreService.getById(genreId));
		}
		book.setName(name);
		book.setDescription(description);
		book.setPrice(price);
		book.setAuthors(authors);
		book.setGenres(genres);
		bookService.save(book);
		return "redirect:/admin/books";
	}

	@RequestMapping(value = "/deleteBook")
	public String delete(@RequestParam int bookId) {
		bookService.deleteById(bookId);
		return "redirect:/admin/books";

	}
}
