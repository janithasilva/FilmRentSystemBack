package com.generation.filmrentsystemback.model.ripositories;

import com.generation.filmrentsystemback.model.entities.Rent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RentRipository extends JpaRepository<Rent, Long>
{
}
