package com.cisco.cmad.blogservice.api;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

public interface UserManager {
	public Response registerUser(User user)  throws DuplicateUserException, InvalidUserException, UserException;

	public Response getUser(int userId) throws UserNotFoundException, UserException;

	public Response updateUser(User user) throws UserNotFoundException, NotAuthorizedException, InvalidUserException, UserException;
	
	public Response login(Credentials credentials) throws InvalidUserException, UserException;
	
	public Response logout(HttpHeaders httpHeaders, String userId) throws InvalidUserException, UserException;

}
