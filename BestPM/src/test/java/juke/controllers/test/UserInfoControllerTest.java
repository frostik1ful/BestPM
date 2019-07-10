package juke.controllers.test;

import static org.mockito.BDDMockito.*;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import juke.controllers.UserInformationController;
import juke.entities.User;
import juke.repositories.UserRepository;
import juke.service.interfases.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserInformationController.class)
public class UserInfoControllerTest {

	private User user;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private UserService userService;

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
	@WithMockUser(username = "elkini")
	public void getBodyResponse() throws Exception {
		given(this.userService.findByUsername("elkini")).willReturn(user);
		this.mockMvc.perform(get("/user/information").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("donate", is(100000)))
				.andExpect(jsonPath("userMoney", is(new Integer(100000))));
	}
}
