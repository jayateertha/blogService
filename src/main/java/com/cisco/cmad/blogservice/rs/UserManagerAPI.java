package com.cisco.cmad.blogservice.rs;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
//import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
public class UserManagerAPI {

	UserManager userMgr = new UserManagerImpl();

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response registerUser(User user) {
		User createdUser = null;

		try {
			createdUser = userMgr.register(user);
		} catch (DuplicateUserException due) {
			due.printStackTrace();
			return Response.status(409).build();
		} catch (InvalidUserException iu) {
			iu.printStackTrace();
			return Response.status(422).build();
		} catch (UserException ue) {
			ue.printStackTrace();
			return Response.status(500).build();
		}

		return Response.status(200).entity(createdUser).build();

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getUser(@Context HttpHeaders httpHeaders, @QueryParam("userId") String userId) {
		if (httpHeaders.getRequestHeader("tocken") == null) {
			return Response.status(401).build();
		}
		String tocken = httpHeaders.getRequestHeader("tocken").get(0);
		System.out.println(tocken);
		System.out.println(userId);
		User user = null;
		try {
			user = userMgr.getUser(tocken, userId);
		} catch (UserNotFoundException unf) {
			unf.printStackTrace();
			return Response.status(404).build();
		} catch (NotAuthorizedException nae) {
			nae.printStackTrace();
			return Response.status(401).build();
		} catch (UserException ue) {
			ue.printStackTrace();
			return Response.status(500).build();
		}

		return Response.status(200).entity(user).build();
	}
	
	@GET
	@Path("/{userId}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
	public Response getUserById(@Context HttpHeaders httpHeaders,@PathParam("userId")String userId) {
		if (httpHeaders.getRequestHeader("tocken") == null) {
			return Response.status(401).build();
		}
		String tocken = httpHeaders.getRequestHeader("tocken").get(0);
		User user = null;
		try {
			user = userMgr.getUser(tocken, userId);
		}catch (UserNotFoundException unf) {
			unf.printStackTrace();
			return Response.status(404).build();
		} catch (NotAuthorizedException nae) {
			nae.printStackTrace();
			return Response.status(401).build();
		} catch (UserException ue) {
			ue.printStackTrace();
			return Response.status(500).build();
		}
		return Response.ok().entity(user).build();
	}

	@PUT
	@Path("/update")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateUser(@Context HttpHeaders httpHeaders, User user) {
		String tocken = httpHeaders.getRequestHeader("tocken").get(0);
/*		System.out.println("In update user function.");
		System.out.println(tocken);
		System.out.println(user.getEmailId());
		System.out.println("************************");
*/		User updateUser = null;
		try {
			updateUser = userMgr.updateUser(tocken, user);
		} catch (InvalidUserException iu) {
			iu.printStackTrace();
			return Response.status(422).build();
		} catch (UserNotFoundException unf) {
			unf.printStackTrace();
			return Response.status(404).build();
		} catch (NotAuthorizedException nae) {
			nae.printStackTrace();
			return Response.status(401).build();
		} catch (UserException ue) {
			return Response.status(500).build();
		}

		return Response.status(200).entity(updateUser).build();
	}

	@POST
	@Path("/login")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })

	public Response login(Credentials credentials) {

		Session session = null;
		System.out.println(credentials.getEmailId());
		try {
			session = userMgr.login(credentials);
		} catch (InvalidUserException iue) {
			return Response.status(401).build();
		} catch (UserException ue) {
			ue.printStackTrace();
			return Response.status(500).build();
		}
		return Response.status(200).entity(session).build();
		// return Response.status(200).header(AUTHORIZATION,"Bearer " +
		// session.getTocken()).build();
	}

	@DELETE
	@Path("/logout")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })

	public Response logout(@Context HttpHeaders httpHeaders, Credentials credentials) {
		try {
			String tocken = httpHeaders.getRequestHeader("tocken").get(0);

			userMgr.logout(tocken, credentials);
		} catch (NotAuthorizedException nae) {
			nae.printStackTrace();
			System.out.println("In not authorized user exception");
			return Response.status(401).build();
		} catch (InvalidUserException iue) {
			System.out.println("In invalid user exception");
			return Response.status(401).build();
		} catch (UserException ue) {
			ue.printStackTrace();
			System.out.println("In user exception");
			return Response.status(500).build();
		}


		return Response.status(200).entity(credentials).build();
	}

}
