package com.cisco.cmad.blogservice.api;

public interface UserManager {
	public User registerUser(User user)  throws DuplicateUserException, InvalidUserException, UserException;

	public User getUser(int userId) throws UserNotFoundException, UserException;

	public User updateUser(User user) throws UserNotFoundException, NotAuthorizedException, InvalidUserException, UserException;
	
	public User login(Credentials credentials) throws InvalidUserException, UserException;
	
	public User logout(int userId) throws InvalidUserException, UserException;

}
