package com.generation.filmrentsystemback.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RentOutputDto
{
	private long id;
	private LocalDate start;
	private LocalDate end;
	private boolean paid;
	private double cost;
	private String filmTitle;
}
