package com.generation.filmrentsystemback.dtos;

import com.generation.filmrentsystemback.model.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto
{
	private String email;
	private LocalDate dob;
	private Role role;
}
