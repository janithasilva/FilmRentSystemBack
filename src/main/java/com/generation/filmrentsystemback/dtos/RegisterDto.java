package com.generation.filmrentsystemback.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterDto
{
	private String ssn;
	private String email;
	private String password;
	private LocalDate dob;
}
