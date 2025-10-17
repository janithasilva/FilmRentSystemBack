package com.generation.filmrentsystemback.model.ripositories;

import com.generation.filmrentsystemback.model.entities.Rent;
import com.generation.filmrentsystemback.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RentRipository extends JpaRepository<Rent, Long>
{
	List<Rent> findByUser(User user);
}
