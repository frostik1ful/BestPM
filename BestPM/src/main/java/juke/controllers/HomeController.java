package juke.controllers;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController implements ErrorController {

	private final String ERROR_PAGE = "/error";

	@RequestMapping(value = "/")
	public String home() {
		return "index";
	}

	@RequestMapping(value = "/authors")
	public String authors() {
		return "authors";
	}

	@RequestMapping(value = "/error")
	public String errorPage() {
		return "pageNotFound";
	}

	@Override
	public String getErrorPath() {
		return ERROR_PAGE;
	}

}