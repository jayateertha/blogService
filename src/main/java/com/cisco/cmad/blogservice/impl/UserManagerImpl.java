package com.cisco.cmad.blogservice.impl;

import java.util.UUID;

import javax.ws.rs.core.HttpHeaders;
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
import com.cisco.cmad.blogservice.dao.api.UserDAO;
import com.cisco.cmad.blogservice.dao.jpa.JPAUserDAO;

public class UserManagerImpl implements UserManager {

	UserDAO userDAO = new JPAUserDAO();
	@Override
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
	@Override
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
	@Override
	public void logout(String tocken, Credentials credentials) throws InvalidUserException, UserException {
		userDAO.deleteSession(credentials.getUsername());
	}

	private String generateTocken() {
		return UUID.randomUUID().toString();

	}
	@Override
	public User getUser(int userId) throws UserNotFoundException, UserException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public User updateUser(User user)
			throws UserNotFoundException, NotAuthorizedException, InvalidUserException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

	


}
