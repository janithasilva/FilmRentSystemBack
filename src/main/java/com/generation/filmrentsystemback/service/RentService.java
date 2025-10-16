package com.generation.filmrentsystemback.service;

import com.generation.filmrentsystemback.model.ripositories.RentRipository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentService
{
	@Autowired
	RentRipository repo;


}
