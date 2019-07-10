package juke.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import juke.entities.Motivator;
import juke.entities.PasswordResetToken;
import juke.entities.Project;
import juke.entities.SmtpMailSender;
import juke.entities.User;
import juke.entities.UserMotivators;
import juke.repositories.PasswordResetTokenRepository;
import juke.repositories.UserRepository;
import juke.service.interfases.MotivatorService;
import juke.service.interfases.ProjectService;
import juke.service.interfases.SecurityService;
import juke.service.interfases.UserService;
import juke.validator.UserAddValidator;
import juke.validator.UserUpdateProfileValidator;
import utils.MotivatorType;

@Controller
@RequestMapping("/user")
@Scope("request")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserAddValidator userAddValidator;

	@Autowired
	private UserUpdateProfileValidator userUpdateProfileValidator;

	@Autowired
	private SmtpMailSender smtpMailSender;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private MotivatorService motivatorService;

	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String addUser(Model model) {
		model.addAttribute("userForm", new User());
		return "userAdd";
	}

	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	public String addUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
		userAddValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "userAdd";
		}
		userService.save(userForm);
		securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());
		return "redirect:/office/myOffice";
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public String loginPage(Model model, String error, String logout) {
		if (error != null) {
			model.addAttribute("error", "Имя пользователя или пароль введены не верно");
		}
		if (logout != null) {
			model.addAttribute("message", "Вы вышли из системы");
		}

		return "userLogin";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		model.addAttribute("message", "Вы вышли из системы");
		return "/";
	}

	@RequestMapping(value = "/profile", method = { RequestMethod.GET })
	public String userProfile(Model model, HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		User user = userService.findByUsername(username);
		model.addAttribute("userForm", user);
		// передаем url приложения во вью
		model.addAttribute("appUrl", getAppUrl(request) + "/resources/images/");
		return "userProfile";
	}

	@RequestMapping(value = "/imageUpload", method = { RequestMethod.POST })
	public String userImageUpload(Model model, @RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String username = request.getUserPrincipal().getName();
		// НЕ ЗАБЫВАЕМ ПРО ОТЛИЧИЯ ВИНДОВЫХ И ЛИНУКСОВЫХ ПУТЕЙ!!
		// Linux path
		// String imagesFolderPath = request.getServletContext().getRealPath("")
		// + "/WEB-INF/classes/public/resources/images";
		// Windows path
		String imagesFolderPath = request.getServletContext().getRealPath("")
				+ "\\WEB-INF\\classes\\public\\resources\\images";
		// presentation paths
		// String imagesFolderPath =
		// "C:\\Users\\Serge\\git\\juke\\src\\main\\resources\\public\\resources\\images";
		User user = userService.findByUsername(username);
		if (!file.isEmpty()) {
			// удаляем старый файл если он не стандартный
			if (!user.getUserImage().equalsIgnoreCase("defaultUser.png")) {
				File oldFile = new File(imagesFolderPath, user.getUserImage());
				oldFile.delete();
			}
			String fileName = file.getOriginalFilename();
			Random random = new Random();
			// сохраняем расширение файла и даем ему название равное username + случайное
			// значение для отсутствия кеширования в браузере для новых файлов
			fileName = username + random.nextInt(10000)
					+ fileName.substring(fileName.lastIndexOf('.'), fileName.length());

			File imageFile = new File(imagesFolderPath, fileName);

			try {
				file.transferTo(imageFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			user.setUserImage(fileName);
			userRepository.save(user);
		} else {
			// если file пустой - ставим юзеру дефолтную картинку
			user.setUserImage("defaultUser.png");
			userRepository.save(user);
		}
		return "redirect:/user/profile";
	}

	@RequestMapping(value = "/forgotPassword", method = { RequestMethod.GET })
	public String forgotPassword(Model model, String error) {
		return "forgotPassword";
	}

	@RequestMapping(value = "/forgotPassword", method = { RequestMethod.POST })
	public String forgotPassword(Model model, final HttpServletRequest request,
			@RequestParam("email") final String userEmail) {
		// если отправили поле без адреса
		if (userEmail == null) {
			model.addAttribute("error", "mailWasNotFound");
			return "forgotPassword";
		}
		User user = userService.findByEmail(userEmail);
		if (user != null) {
			final String token = UUID.randomUUID().toString();
			userService.createPasswordResetTokenForUser(user, token);

			// отправка письма с токеном
			try {
				smtpMailSender.send(userEmail, "Восстановление пароля, BestPM", "Ссылка для восстановления пароля: "
						+ getAppUrl(request) + "/user/resetPassword?token=" + token);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			model.addAttribute("error", "mailWasNotFound");
			return "forgotPassword";
		}
		return "index";
	}

	// Reset password
	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPassword(final Model model, @RequestParam("token") final String token) {

		// проверка наличия токена в базе и редирект на страницу ввода нового пароля
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
		if (passwordResetToken != null) {
			final Calendar cal = Calendar.getInstance();
			// проверка пригодности токена по сроку давности
			if ((passwordResetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
				// возвращаем ошибку если срок токена истек
				model.addAttribute("error", "outdatedToken");
				return "forgotPassword";
			}
			// если с токеном все окей - отправляем юзера менять пароль
			model.addAttribute("userToken", passwordResetToken.getToken());
			return "updatePassword";
		}

		// возвращаем ошибку если токен не верный
		model.addAttribute("error", "wrongToken");
		return "forgotPassword";
	}

	@RequestMapping(value = "/updatePassword", method = { RequestMethod.GET })
	public String updatePassword(Model model, String error) {
		return "updatePassword";
	}

	@RequestMapping(value = "/updatePassword", method = { RequestMethod.POST })
	public String updatePassword(@ModelAttribute("newPassword") String newPassword,
			@ModelAttribute("newPasswordConfirm") String newPasswordConfirm,
			@ModelAttribute("userToken") String userToken, Model model) {
		// passwords match rules:
		if (newPassword.equals(newPasswordConfirm) && newPassword.length() >= 6 && newPassword.length() <= 32) {
			PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(userToken);
			User user = passwordResetToken.getUser();
			user.setPassword(newPassword);
			userService.save(user);
			return "login";
		}
		model.addAttribute("error", "WrongOrMismatchPassword");
		return "updatePassword";
	}

	@RequestMapping(value = "/updateProfile", method = { RequestMethod.GET })
	public String userUpdateProfile(Model model) {
		String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userRepository.findByUsername(currentUserName);
		// чтобы не отдавать соленый пароль во вью - сбросим его перед отправкой
		currentUser.setPassword("");
		model.addAttribute("userForm", currentUser);
		return "updateProfile";
	}

	@RequestMapping(value = "/updateProfile", method = { RequestMethod.POST })
	public String userUpdateProfile(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			Model model) {
		userUpdateProfileValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "updateProfile";
		}
		User user = userService.findByUsername(userForm.getUsername());
		user.setPassword(userForm.getPassword());
		user.setEmail(userForm.getEmail());
		userService.save(user);
		return "redirect:/user/logout";
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	// <Fixed by Tkachenko>
	@RequestMapping(value = "/addProject", method = RequestMethod.GET)
	public String addProject(Integer id) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (projectService.getProjectById(id).getUser() != null) {
			return "redirect:/projects/home?error=projectAlreadyHaveUser";
		}
		userService.addProject(userName, id);
		return "redirect:/userProjects/";
	}

	@RequestMapping(value = "/myProgrammers", method = RequestMethod.GET)
	public String getProgrammers(Model model, @RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "succsess", required = false) String succsess) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute("programmers", userService.getUserProgrammers(userName));
		if (error != null) {
			model.addAttribute("error", error);
		}
		if (succsess != null) {
			model.addAttribute("succsess", succsess);
		}
		Set<Project> projects = userService.getProjects(userName);
		if (projects.size() > 0) {
			model.addAttribute("projects", projects);
		}
		return "userProgrammers";
	}

	@RequestMapping(value = "/buyMotivator", method = RequestMethod.POST, params = { "motivatorId" })
	public ModelAndView buyMotivator(ModelMap model, Integer motivatorId) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Motivator motivator = motivatorService.getById(motivatorId);
		if (userService.buyMotivator(userName, motivator)) {
			model.addAttribute("success", "Поздравляем вас с покупкой");
			return new ModelAndView("redirect:/user/myMotivators", model);
		} else {
			model.addAttribute("error", "Увы, но у вас не хватает денег. Рекомендуем вам взять кредит!");
			return new ModelAndView("redirect:/motivator/market", model);
		}
	}

	@RequestMapping(value = "/buyMotivator/donate", method = RequestMethod.POST, params = { "motivatorId" })
	public ModelAndView buyMotivatorDonate(ModelMap model, Integer motivatorId) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		Motivator motivator = motivatorService.getById(motivatorId);
		if (userService.buyMotivatorDonate(userName, motivator)) {
			model.addAttribute("success", "Поздравляем вас с покупкой");
			return new ModelAndView("redirect:/user/myMotivators", model);
		} else {
			model.addAttribute("error", "Увы, но у вас не хватает денег. Рекомендуем пополнить баланс!");
			return new ModelAndView("redirect:/motivator/market", model);
		}
	}

	@RequestMapping(value = "/myMotivators", method = RequestMethod.GET)
	public String myMotivators(Model model, @RequestParam(name = "active", defaultValue = "1") Integer active,
			@RequestParam(name = "success", required = false) String success) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		List<UserMotivators> motivators = null;
		String recomendation = null;
		if (active == 1) {
			motivators = userService.getUserMotivators(userName, true);
			recomendation = "На данный момент у вас активных мотиваторов.";
			model.addAttribute("activeFirst", "active");
		} else {
			motivators = userService.getUserMotivators(userName, false);
			recomendation = "На данный момент у вас нет не активных мотиваторов.";
			model.addAttribute("activeSecond", "active");
			System.err.println(motivators);
			System.err.println(active);
		}
		if (motivators.size() > 0) {
			model.addAttribute("motivators", motivators);
			model.addAttribute("type", MotivatorType.TEMPORARY);
		} else {
			model.addAttribute("recomendation", recomendation);
		}
		if (success != null) {
			model.addAttribute("success", success);
		}
		model.addAttribute("active", active);
		return "userMotivators";
	}

	@RequestMapping(value = "/byOffice", params = { "officeId" }, method = RequestMethod.POST)
	public ModelAndView byOffice(int officeId, ModelMap model) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.addOffice(userName, officeId)) {
			model.addAttribute("success", "Поздравляем. У вас теперь новый офис!");
		} else {
			model.addAttribute("error", "Увы, но у вас не хватает денег.Рекомендуем пополнить баланс!");
		}
		System.err.println(model);
		return new ModelAndView("redirect:/office/myOffice", model);
	}

	@RequestMapping(value = "/byOffice/donate", params = { "officeId" }, method = RequestMethod.POST)
	public ModelAndView byOfficeDonate(int officeId, ModelMap model) {
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userService.addOfficeDonate(userName, officeId)) {
			model.addAttribute("success", "Поздравляем. У вас теперь новый офис!");
		} else {
			model.addAttribute("error", "Увы, но у вас не хватает денег.Рекомендуем пополнить баланс!");
		}
		return new ModelAndView("redirect:/office/myOffice", model);
	}

	// </Fixed by Tkachenko>
	@RequestMapping(value = "/listOfUsers", method = RequestMethod.GET)
	public String listOfUsers(Model model) {
		model.addAttribute("userList", userRepository.findAll());
		return "listOfUsers";
	}

	@RequestMapping(value = "/banned", method = RequestMethod.GET)
	public String banned(String username) {
		userService.changeBanned(username);
		return "redirect:/user/listOfUsers";
	}
}