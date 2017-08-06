package com.cisco.cmad.blogservice.api;

public interface UserManager {
	public User register(User user) throws DuplicateUserException, InvalidUserException, UserException;

	public User getUser(String tocken, String userId) throws UserNotFoundException, UserException;

	public User updateUser(String tocken, User user)
			throws UserNotFoundException, NotAuthorizedException, InvalidUserException, UserException;

	public Session login(Credentials credentials) throws InvalidUserException, UserException;

	public void logout(String tocken, Credentials credentials) throws InvalidUserException, UserException;

//	User getUser(String tocken) throws UserNotFoundException, NotAuthorizedException, UserException;

}
