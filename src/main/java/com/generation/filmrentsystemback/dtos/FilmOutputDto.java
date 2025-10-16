package com.generation.filmrentsystemback.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class FilmOutputDto
{
	private Long id;
	private String title;
	private List<String> genres;
	private String director;
	private String description;
	private String pegi;
	private LocalDate releaseDate;
}
