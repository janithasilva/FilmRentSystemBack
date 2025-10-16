package com.generation.filmrentsystemback.model.ripositories;

import com.generation.filmrentsystemback.dtos.FilmOutputDto;
import com.generation.filmrentsystemback.model.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FilmRipository extends JpaRepository<Film, Long>
{
	Film findByTitle(String title);
}
