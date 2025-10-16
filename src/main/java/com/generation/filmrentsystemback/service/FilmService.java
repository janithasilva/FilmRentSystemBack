package com.generation.filmrentsystemback.service;

import com.generation.filmrentsystemback.dtos.FilmInputDto;
import com.generation.filmrentsystemback.dtos.FilmOutputDetailedDto;
import com.generation.filmrentsystemback.dtos.FilmOutputDto;
import com.generation.filmrentsystemback.model.entities.Film;
import com.generation.filmrentsystemback.model.ripositories.FilmRipository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FilmService
{

	@Autowired
	FilmRipository repo;

	public List<FilmOutputDetailedDto> getAllFilmsAsDtos()
	{
		List<Film> films = repo.findAll();

		return films.stream().map(f -> convertFilmToDetailedDto(f)).toList();
	}

	public List<FilmOutputDto> getAllAvailableFilmsAsDtos()
	{

		List<Film> films = repo.findAll();
		List<FilmOutputDto> filmsAsDtos = new ArrayList<>();

		for(Film f: films)
			if(f.getTotalUnits() > 0)
				filmsAsDtos.add(convertFilmToDto(f));

		return filmsAsDtos;
	}

	public FilmOutputDto getFilmByTitle(String title)
	{
		Film f = repo.findByTitle(title);

		if(f == null){
			throw new NoSuchElementException("There is no film with the title "+title);
		}

		return convertFilmToDto(f);
	}

	public void save(FilmInputDto dto)
	{
		Film f = new Film();

		f.setTitle(dto.getTitle());
		f.setGenres(dto.getGenres());
		f.setDirector(dto.getDirector());
		f.setDescription(dto.getDescription());
		f.setPegi(dto.getPegi());
		f.setReleaseDate(dto.getReleaseDate());
		f.setTotalUnits(dto.getTotalUnits());
		f.setBasePrice(dto.getBasePrice());

		repo.save(f);
	}

	public FilmOutputDto convertFilmToDto(Film f)
	{
		FilmOutputDto dto = new FilmOutputDto();
		dto.setId(f.getId());
		dto.setTitle(f.getTitle());
		dto.setDirector(f.getDirector());
		dto.setDescription(f.getDescription());
		dto.setGenres(f.getGenres());
		dto.setPegi(f.getPegi());
		dto.setReleaseDate(f.getReleaseDate());

		return dto;
	}

	public FilmOutputDetailedDto convertFilmToDetailedDto(Film f)
	{
		FilmOutputDetailedDto dto = new FilmOutputDetailedDto();
		dto.setId(f.getId());
		dto.setTitle(f.getTitle());
		dto.setDirector(f.getDirector());
		dto.setDescription(f.getDescription());
		dto.setGenres(f.getGenres());
		dto.setPegi(f.getPegi());
		dto.setReleaseDate(f.getReleaseDate());
		dto.setBasePrice(f.getBasePrice());
		dto.setTotalUnits(f.getTotalUnits());
		dto.setAvailableUnits(f.availableUnits());
		dto.setTotalRevenue(f.totalRevenue());
		dto.setRevenuePerMonth(f.revenuePerMonth());

		return dto;
	}
}
