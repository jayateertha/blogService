package com.cisco.cmad.blogservice.api;

import javax.ws.rs.core.HttpHeaders;

public interface UserManager {
	public User registerUser(User user)  throws DuplicateUserException, InvalidUserException, UserException;

	public User getUser(int userId) throws UserNotFoundException, UserException;

	public User updateUser(User user) throws UserNotFoundException, NotAuthorizedException, InvalidUserException, UserException;
	
	public Session login(Credentials credentials) throws InvalidUserException, UserException;
	
	public void logout(HttpHeaders httpHeaders, int userId) throws InvalidUserException, UserException;

}
