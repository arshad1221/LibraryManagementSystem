package com.library.management.BookStore.modal;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewUser {

	@Max(value = 10, message = "Maximum 10 character")
	private String name;

	@Email
	private String email;

	@NotEmpty
	private String password;

}
