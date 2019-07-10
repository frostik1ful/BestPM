package juke.controllers.test;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import juke.controllers.UserController;
import juke.entities.SmtpMailSender;
import juke.entities.User;
import juke.repositories.PasswordResetTokenRepository;
import juke.repositories.UserRepository;
import juke.service.interfases.MotivatorService;
import juke.service.interfases.ProjectService;
import juke.service.interfases.SecurityService;
import juke.service.interfases.UserService;
import juke.validator.UserAddValidator;
import juke.validator.UserUpdateProfileValidator;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	private final String defaultURL = "/user/";

	private User user;

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private BindingResult bindingResult;

	@MockBean
	private UserService userService;

	@MockBean
	private SecurityService securityService;

	@MockBean
	private UserAddValidator userAddValidator;

	@MockBean
	private UserUpdateProfileValidator userUpdateProfileValidator;

	@MockBean
	private SmtpMailSender smtpMailSender;

	@MockBean
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@MockBean
	private MotivatorService motivatorService;

	@MockBean
	private ProjectService projectService;

	@Before
	public void setup() {
		user = new User();
		user.setUsername("elkini");
		user.setBalance(100000L);
		user.setDonate(100000l);
		user.setEmail("dfsfkk@s,dfsj.asd");
		user.setId(1L);
	}

	@Test
	@WithMockUser
	public void testUserAdd() throws Exception {
		this.mvc.perform(get(defaultURL + "add").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(forwardedUrl("userAdd"));
	}

	@Test
	@WithMockUser
	public void loginTest() throws Exception {
		this.mvc.perform(get(defaultURL + "login").param("error", "error").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andExpect(forwardedUrl("userLogin"))
				.andExpect(model().attribute("error", "Имя пользователя или пароль введены не верно"));

		this.mvc.perform(get(defaultURL + "login").param("logout", "logout").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk()).andExpect(forwardedUrl("userLogin"))
				.andExpect(model().attribute("message", "Вы вышли из системы"));

	}

	@Test
	@WithMockUser
	public void logoutTest() throws Exception {
		this.mvc.perform(get(defaultURL + "logout").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(forwardedUrl("/"));

	}

	@Test
	@WithMockUser
	public void logoutTestWithUser() throws Exception {
		this.mvc.perform(get(defaultURL + "logout").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
				.andExpect(forwardedUrl("/")).andExpect(model().attribute("message", "Вы вышли из системы"));

	}
}
