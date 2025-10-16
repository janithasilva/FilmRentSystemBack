package com.generation.filmrentsystemback.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Film
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;
	private List<String> genres;
	private String director;
	private String description;
	private String pegi;
	private LocalDate releaseDate;
	private int totalUnits;
	private double basePrice;

	@OneToMany(mappedBy = "film")
	private List<Rent> rents;

	public int availableUnits() {
		long rentedCount = rents.stream()
				.filter(r -> r.getEnd() == null)
				.count();
		return (int)(totalUnits - rentedCount);
	}

	public double totalRevenue() {
		return rents.stream()
				.mapToDouble(Rent::calculateCost)
				.sum();
	}

	public double revenuePerMonth(){
		return totalRevenue()/12;
	}
}
