package juke.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	public static final String DEFAULT_ERROR_VIEW = "errorPage";
	public static final String EXCUSES = "Проект находится на стадии бета тестирования, приносим свои извинения.";

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultExeptionHandler(HttpServletRequest req, Exception e) throws Exception {
		// Если исключение было аннотировано с помощью @ResponseStatus, то его
		// реконструировать и разрешить
		// Framework обрабатывает его - например, пример OrderNotFoundException
		// в начале этого сообщения.
		// AnnotationUtils - это класс утилиты Spring Framework.
		if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
			throw e;
		// В противном случае настройте и отправьте пользователю сообщение об ошибке по
		// умолчанию.
		return errorView(req.getRequestURL().toString());
	}

	private ModelAndView errorView(String url) {
		ModelAndView modelAndView = new ModelAndView(DEFAULT_ERROR_VIEW);
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> entity = template.getForEntity(url, String.class);
		HttpStatus statusCode = entity.getStatusCode();
		modelAndView.addObject("status", statusCode);
		modelAndView.addObject("url", url);
		modelAndView.addObject("excuses", EXCUSES);
		return modelAndView;
	}

}
