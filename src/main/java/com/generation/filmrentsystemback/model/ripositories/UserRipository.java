package com.generation.filmrentsystemback.model.ripositories;

import com.generation.filmrentsystemback.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRipository extends JpaRepository<User, String>
{
	Optional<User> findByEmailAndPassword(String email, String hash);

	Optional<User> findByToken(String token);
}
