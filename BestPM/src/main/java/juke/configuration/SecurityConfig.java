package juke.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/", "/authors", "/user/add", "/user/login", "/user/logout", "/user/forgotPassword",
						"/user/resetPassword", "/user/updatePassword", "/webjars/**", "/resources/**")
				.permitAll()
				.antMatchers("/player", "/programmer/market", "/programmer/hire", "/programmer/hireCall",
						"/programmer/appoint", "/programmer/fire", "/projects/home", "/office/**", "/user/buyMotivator",
						"/user/myMotivators", "/user/buyMotivator/donate", "/motivator/market", "/credit/**",
						"/userProjects/", "/userProjects/**", "/user/information", "/user/myProgrammers",
						"/user/addProject", "/user/imageUpload", "/user/updateProfile", "/user/profile",
						"/user/byOffice", "/user/byOffice/donate")
				.hasAnyAuthority("USER", "ADMIN").antMatchers("/**").hasAuthority("ADMIN").anyRequest()
				.fullyAuthenticated().and().formLogin().loginPage("/user/login").failureUrl("/user/login?error=error")
				.usernameParameter("username").permitAll().and().logout().logoutUrl("/user/login?logout=logout")
				.deleteCookies("remember-me").logoutSuccessUrl("/").permitAll().and().rememberMe();

		http.formLogin()
				// указываем начальную страницу
				.loginPage("/").failureUrl("/user/login?error=error").defaultSuccessUrl("/office/myOffice", true).and()
				.sessionManagement().maximumSessions(1); // avoiding multiple login

		http.exceptionHandling().accessDeniedPage("/accessDenied");
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
