package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.services.AccountService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.formLogin(login -> login
				.loginPage("/login")
				.defaultSuccessUrl("/userhomepage",true)
				.usernameParameter("userName")
				.passwordParameter("userPassword")
				.failureUrl("/loginerror")
				.loginProcessingUrl("/user-login")
				.permitAll()
		).logout(logout -> logout
				.logoutSuccessUrl("/homepage")
		).authorizeHttpRequests(authz -> authz
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.requestMatchers("/homepage","/register","/login").permitAll()
                .anyRequest().authenticated()
        );
		return http.build();
	}
	
	public static UserDetailsManager manager = null;
	@Autowired
	private AccountService accountService;
	@Bean
	public UserDetailsService userDetailsService() {
		List<UserDetails> users = accountService.getAllAccounts().stream().map(
				account -> User
				.withDefaultPasswordEncoder()
				.username(account.getUserName())
				.password(account.getUserPassword())
				.roles("USER")
				.build()
				).toList();
		
		manager = new InMemoryUserDetailsManager(users);
		manager.createUser(User
				.withDefaultPasswordEncoder()
				.username("Alice")
				.password("ABC12345")
				.roles("USER")
				.build());
		return manager;
	}
	
	public static void addUser(String userName, String userPassword) {
		manager.createUser(User
				.withDefaultPasswordEncoder()
				.username(userName)
				.password(userPassword)
				.roles("USER")
				.build());
	}
}