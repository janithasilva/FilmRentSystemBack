package com.generation.filmrentsystemback.service;

import com.generation.filmrentsystemback.dtos.RentOutputDto;
import com.generation.filmrentsystemback.model.entities.Film;
import com.generation.filmrentsystemback.model.entities.Rent;
import com.generation.filmrentsystemback.model.entities.User;
import com.generation.filmrentsystemback.model.ripositories.FilmRipository;
import com.generation.filmrentsystemback.model.ripositories.RentRipository;
import com.generation.filmrentsystemback.model.ripositories.UserRipository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RentService
{
	@Autowired
	RentRipository rentRepo;

	@Autowired
	UserService userServe;

	@Autowired
	FilmService filmServe;

	public List<RentOutputDto> getAllrentsByUser(String token)
	{
		User user = userServe.findUserByToken(token);
		List<Rent> rents = rentRepo.findByUser(user);

		return rents.stream().map(r -> convertRentToDto(r)).toList();
	}

	public List<RentOutputDto> getCurrentrentsByUser(String token)
	{
		User user = userServe.findUserByToken(token);
		List<Rent> rents = rentRepo.findByUser(user);

		return rents.stream().filter(r -> r.getEnd() == null).map(r -> convertRentToDto(r)).toList();
	}

	public List<RentOutputDto> getRentsToPayByUser(String token)
	{
		User user = userServe.findUserByToken(token);
		List<Rent> rents = rentRepo.findByUser(user);

		return rents.stream().filter(r -> r.getEnd() != null && !r.isPaid()).map(r -> convertRentToDto(r)).toList();
	}

	public void createRent(String token, String filmTitle)
	{
		User user = userServe.findUserByToken(token);

		Film film = filmServe.getFilmByTitle(filmTitle);

		Rent rent = new Rent();
		rent.setUser(user);
		rent.setFilm(film);
		rent.setStart(LocalDate.now());
		rent.setPaid(false);
		rent.setEnd(null);

		rentRepo.save(rent);
	}

	public void setRentPaid(Long id)
	{
		Optional<Rent> op = rentRepo.findById(id);

		if(op.isEmpty())
			throw new NoSuchElementException("There is no Rent with the id: "+id);

		Rent rent = op.get();
		rent.setPaid(true);
		rentRepo.save(rent);
	}

	public void setRestitution(Long id)
	{
		Optional<Rent> op = rentRepo.findById(id);

		if(op.isEmpty())
			throw new NoSuchElementException("There is no Rent with the id: "+id);

		Rent rent = op.get();
		rent.setEnd(LocalDate.now());
		rentRepo.save(rent);
	}

	private RentOutputDto convertRentToDto(Rent rent)
	{
		RentOutputDto dto = new RentOutputDto();

		dto.setId(rent.getId());
		dto.setStart(rent.getStart());
		dto.setEnd(rent.getEnd());
		dto.setPaid(rent.isPaid());
		dto.setCost(rent.calculateCost());
		dto.setFilmTitle(rent.getFilm().getTitle());

		return dto;
	}
}
