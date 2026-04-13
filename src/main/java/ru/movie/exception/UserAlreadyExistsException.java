package ru.movie.exception;

public class UserAlreadyExistsException extends RuntimeException {
  public UserAlreadyExistsException(String username) {
    super("User already exists: " + username);
  }
}
