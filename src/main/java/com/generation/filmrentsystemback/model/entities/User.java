package com.generation.filmrentsystemback.model.entities;

import com.generation.filmrentsystemback.model.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User
{
	@Id
	private String ssn;
	@NotNull @NotBlank @Column(unique = true)
	private String email;
	private String password;
	private LocalDate dob;
	private Role role;
	private String token;

	@OneToMany(mappedBy = "user")
	List<Rent> rents;

	public double calculateDebit()
	{
		return rents.stream()
				.filter(r -> !r.isPaid() && r.getEnd() != null)
				.mapToDouble(Rent::calculateCost)
				.sum();
	}
}
