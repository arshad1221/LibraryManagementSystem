package com.library.management.BookStore.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Passwordservice {

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	private PasswordEncoder passwordEncoder;

	public void PasswordService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String encodePassword(String rawPassword) {
		return passwordEncoder.encode(rawPassword);
	}

}
