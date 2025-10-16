package com.generation.filmrentsystemback.controllers;

import com.generation.filmrentsystemback.dtos.LoginDto;
import com.generation.filmrentsystemback.dtos.RegisterDto;
import com.generation.filmrentsystemback.dtos.UserDto;
import com.generation.filmrentsystemback.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController
{
	@Autowired
	UserService serv;

	@PostMapping("register")
	public void register(@RequestBody RegisterDto dto, HttpServletResponse res)
	{
		String userToken = serv.register(dto);

		Cookie cookie = new Cookie("token", userToken);
		cookie.setMaxAge(3600);
		cookie.setPath("/");
		res.addCookie(cookie);
	}

	@PostMapping("login")
	public void login(@RequestBody LoginDto dto, HttpServletResponse res)
	{
		String userToken = serv.login(dto);

		Cookie cookie = new Cookie("token", userToken);
		cookie.setMaxAge(3600);
		cookie.setPath("/");
		res.addCookie(cookie);

		System.out.println(userToken);
	}

	@GetMapping("/userinformation")
	public UserDto getUserInformation(@CookieValue(required = false) String token)
	{
		if(token == null)
			return null;
		return serv.readUserDto(token);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String gestisciTutto(Exception e)
	{
		return "Operazione fallita, ulteriori dettagli "+e.getMessage();
	}
}
