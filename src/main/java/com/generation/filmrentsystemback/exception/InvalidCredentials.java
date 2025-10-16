package com.generation.filmrentsystemback.exception;

public class InvalidCredentials extends RuntimeException
{
	public InvalidCredentials(String message)
	{
		super(message);
	}
}
