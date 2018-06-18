package bs.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bs.models.Book;
import bs.models.Order;
import bs.services.interfaces.BookService;
import bs.services.interfaces.OrderService;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/all")
	public ModelAndView all(ModelMap model) {
		model.addAttribute("orders", orderService.getAll());
		return new ModelAndView("orders", model);

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String address,
			@RequestParam Integer bookId, @RequestParam Integer quantity) {
		Book book = bookService.getById(bookId);
		Order order = new Order(firstName, lastName, address, book.getPrice(), quantity, bookId, book.getName());
		orderService.save(order);
		return "redirect:/";
	}

	@RequestMapping(value = "/delete")
	public String delete(@RequestParam int orderId) {
		orderService.deleteById(orderId);
		return "redirect:/order/all";

	}
}
