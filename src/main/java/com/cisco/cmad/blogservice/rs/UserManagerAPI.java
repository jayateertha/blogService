package com.cisco.cmad.blogservice.rs;

import javax.ws.rs.DELETE;
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
		} catch (DuplicateUserException due) {
			due.printStackTrace();
			return Response.status(409).build();
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

	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response login(Credentials credentials) throws InvalidUserException, UserException {

		Session session = null;
		try {
			System.out.println("name:" + credentials.getUsername() + " Pass:" + credentials.getPassword());
			session = userMgrImpl.login(credentials);
		} catch (InvalidUserException iue) {
			return Response.status(401).build();
		} catch (UserException ue) {
			ue.printStackTrace();
			return Response.status(500).build();
		}
		return Response.status(200).entity(session).build();
	}

	@DELETE
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Override
	public Response logout(@Context HttpHeaders httpHeaders, Credentials credentials) throws InvalidUserException, UserException {
		String tocken = httpHeaders.getRequestHeader("tocken").get(0);
		userMgrImpl.logout(tocken, credentials.getUsername());
		return Response.status(200).build();
	}

}
