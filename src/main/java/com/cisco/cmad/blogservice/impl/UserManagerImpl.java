package com.cisco.cmad.blogservice.impl;

import java.util.UUID;

import javax.ws.rs.Path;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.DuplicateUserException;
import com.cisco.cmad.blogservice.api.InvalidUserException;
import com.cisco.cmad.blogservice.api.Session;
import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.api.UserException;
import com.cisco.cmad.blogservice.dao.api.UserDAO;
import com.cisco.cmad.blogservice.dao.jpa.JPAUserDAO;

@Path("/user")
public class UserManagerImpl {

	UserDAO userDAO = new JPAUserDAO();

	public User register(User user) throws DuplicateUserException, InvalidUserException, UserException {
		if ((user.getName() == null) || user.getName().trim().equals("") || (user.getEmailId() == null)
				|| user.getEmailId().trim().equals("")) {
			throw new InvalidUserException();
		}

		if (userDAO.isExists(user.getEmailId())) {
			throw new DuplicateUserException();
		}

		User createdUser = userDAO.create(user);

		return createdUser;

	}

	public Session login(Credentials credentials) throws InvalidUserException, UserException {

		if (!userDAO.isExists(credentials.getUsername())) {
			System.out.println("User Does not exists");
			throw new InvalidUserException();
		}

		Session session = null;
		if (userDAO.isValid(credentials)) {
			String tocken = generateTocken();
			session = new Session();
			session.setTocken(tocken);
			session.setUserName(credentials.getUsername());
			boolean sessionCreated = userDAO.createSession(session);
			if (sessionCreated) {
				return session;
			} else {
				throw new UserException();
			}
		} else {
			System.out.println("Auth failed");
			throw new InvalidUserException();
		}
		
	}

	public void logout(String tocken, String userId) throws InvalidUserException, UserException {
		userDAO.deleteSession(userId);
	}

	private String generateTocken() {
		return UUID.randomUUID().toString();

	}

}
