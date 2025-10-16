package com.generation.filmrentsystemback.service;

import com.generation.filmrentsystemback.dtos.LoginDto;
import com.generation.filmrentsystemback.dtos.RegisterDto;
import com.generation.filmrentsystemback.dtos.UserDto;
import com.generation.filmrentsystemback.exception.InvalidCredentials;
import com.generation.filmrentsystemback.model.entities.User;
import com.generation.filmrentsystemback.model.enums.Role;
import com.generation.filmrentsystemback.model.ripositories.UserRipository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService
{
	@Autowired
	UserRipository repo;

	public String register(RegisterDto rDto){
		if(!rDto.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
			throw new InvalidCredentials("Password not valid");

		User user = new User();
		user.setSsn(rDto.getSsn());
		user.setEmail(rDto.getEmail());
		String hash = DigestUtils.md5Hex(rDto.getPassword());
		user.setPassword(hash);
		user.setDob(rDto.getDob());
		user.setRole(Role.STANDARD);

		user.setToken(UUID.randomUUID().toString());

		repo.save(user);

		return user.getToken();
	}

	public String login(LoginDto lDto){
		String hash = DigestUtils.md5Hex(lDto.getPassword());

		Optional<User> op = repo.findByEmailAndPassword(lDto.getEmail(), hash);

		if(op.isEmpty())
			throw new InvalidCredentials("Invalid username or password!");

		User user = op.get();

		return user.getToken();
	}

	public User findUserByToken(String token)
	{
		Optional<User> op = repo.findByToken(token);

		if(op.isEmpty())
			throw new InvalidCredentials("InvalidToken");

		return op.get();
	}

	public UserDto readUserDto(String token)
	{
		User user = findUserByToken(token);

		UserDto uDto = new UserDto();
		uDto.setEmail(user.getEmail());
		uDto.setRole(user.getRole());
		uDto.setDob(user.getDob());

		return uDto;
	}



}
