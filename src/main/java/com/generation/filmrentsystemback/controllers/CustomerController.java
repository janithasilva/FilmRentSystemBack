package com.generation.filmrentsystemback.controllers;


import com.generation.filmrentsystemback.dtos.FilmOutputDto;
import com.generation.filmrentsystemback.dtos.RentOutputDto;
import com.generation.filmrentsystemback.model.entities.Rent;
import com.generation.filmrentsystemback.service.FilmService;
import com.generation.filmrentsystemback.service.RentService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer")
public class CustomerController
{
	@Autowired
	FilmService filmServe;

	@Autowired
	RentService rentServe;

	@GetMapping("/films")
	public List<FilmOutputDto> getAllAvailableFilms()
	{
		return filmServe.getAllAvailableFilmsAsDtos();
	}

	@GetMapping("/rents")
	public List<RentOutputDto> getAllRentsByUser(HttpServletRequest req)
	{
		String token = extractToken(req);
		return rentServe.getAllrentsByUser(token);
	}

	@GetMapping("/rents/current")
	public List<RentOutputDto> getCurrentRentsByUser(HttpServletRequest req)
	{
		String token = extractToken(req);
		return rentServe.getCurrentrentsByUser(token);
	}

	@GetMapping("/rents/topay")
	public List<RentOutputDto> getRentsToPayByUser(HttpServletRequest req)
	{
		String token = extractToken(req);
		return rentServe.getRentsToPayByUser(token);
	}

	@PostMapping("rents/{title}")
	public void createRent(@PathVariable String title, HttpServletRequest req)
	{
		String token = extractToken(req);
		rentServe.createRent(token, title);
	}



	private String extractToken(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		throw new RuntimeException("Token not found in cookies");
	}
}
