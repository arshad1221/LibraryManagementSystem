package com.library.management.BookStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/", "/login", "/register", "/save", "/css/**", "/js/**").permitAll() // Public
				// pages
				.requestMatchers("/add").hasRole("Admin") // Restrict "/addnew" to users with ADMIN role
				.anyRequest().authenticated() // Permit
												// public
												// pages
		// Secure other pages
		).formLogin(form -> form.loginPage("/login") // Custom login page
				.defaultSuccessUrl("/books", true) // Redirect to index.html after login
				.permitAll()).logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout") // Redirect
																												// to
																												// login
																												// page
																												// after
																												// logout
						.permitAll());
		return http.build();
	}

	// pasword encoding

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
