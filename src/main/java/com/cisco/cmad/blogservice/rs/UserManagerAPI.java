package com.cisco.cmad.blogservice.rs;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.DuplicateUserException;
import com.cisco.cmad.blogservice.api.InvalidUserException;
import com.cisco.cmad.blogservice.api.NotAuthorizedException;
import com.cisco.cmad.blogservice.api.Session;
import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.api.UserException;
import com.cisco.cmad.blogservice.api.UserManager;
import com.cisco.cmad.blogservice.api.UserNotFoundException;
import com.cisco.cmad.blogservice.impl.UserManagerImpl;

@Path("/user")
public class UserManagerAPI implements UserManager {

	UserManagerImpl userMgrImpl = new UserManagerImpl();

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response registerUser(User user) throws DuplicateUserException, InvalidUserException, UserException {
		User createdUser = null;
		try {
			createdUser = userMgrImpl.register(user);
		} catch (Exception e) {
			return Response.status(500).build();
		}

		return Response.status(200).entity(createdUser).build();

	}

	@Override
	public Response getUser(int userId) throws UserNotFoundException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response updateUser(User user)
			throws UserNotFoundException, NotAuthorizedException, InvalidUserException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response login(Credentials credentials) throws InvalidUserException, UserException {

		Session session = null;
		try {
			session = userMgrImpl.login(credentials);
		} catch (Exception e) {
			return Response.status(500).build();
		}
		return Response.status(200).entity(session).build();
	}

	@Override
	public Response logout(@Context HttpHeaders httpHeaders, String userId) throws InvalidUserException, UserException {
		String tocken = httpHeaders.getRequestHeader("tocken").get(0);
		userMgrImpl.logout(tocken, userId);
		return Response.status(200).build();
	}

}
