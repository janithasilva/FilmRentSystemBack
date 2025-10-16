package com.generation.filmrentsystemback.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
public class Rent
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_ssn")
	private User user;

	@ManyToOne
	@JoinColumn(name = "film_id")
	private Film film;

	private LocalDate start;
	private LocalDate end;
	private boolean paid;

	public double calculateCost()
	{
		if (film == null || start == null) return 0.0;

		double basePrice = film.getBasePrice();
		long days = (end != null)
					? ChronoUnit.DAYS.between(start, end)
					: ChronoUnit.DAYS.between(start, LocalDate.now());

		double cost = basePrice + (days * 0.5);

		if (!paid && end != null) {
			long overdueDays = ChronoUnit.DAYS.between(end, LocalDate.now());
			if (overdueDays > 0) {
				cost += (cost * 0.2 * overdueDays);
			}
		}

		return cost;
	}
}
