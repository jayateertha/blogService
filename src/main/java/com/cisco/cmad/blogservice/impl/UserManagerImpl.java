package com.cisco.cmad.blogservice.impl;

import java.util.UUID;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

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
	public User registerUser(User user) throws DuplicateUserException, InvalidUserException, UserException {
		if ((user.getName() == null) || user.getName().trim().equals("") || (user.getEmailId() == null)
				|| user.getEmailId().trim().equals("")) {
			throw new InvalidUserException();
		}
		
		if(userDAO.isExists(user.getEmailId())) {
			throw new DuplicateUserException();
		}
		
		User createdUser = userDAO.create(user);
		
		return createdUser;
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

	@Override
	public Session login(Credentials credentials) throws InvalidUserException, UserException {
		User user = userDAO.get(credentials.getUsername());
		
		if (!userDAO.isExists(credentials.getUsername())) {
			throw new InvalidUserException();
		}
		
		Session session = null;
		if (userDAO.isValid(credentials)) {
			String tocken = generateTocken();
			boolean sessionCreated = userDAO.createSession(credentials.getUsername(), tocken);
			if (sessionCreated) {
				session = new Session();
				session.setTocken(tocken);
				session.setUserName(credentials.getUsername());
			}
		}
		return session;
	}

	@Override
	public void logout(@Context HttpHeaders httpHeaders, int userId) throws InvalidUserException, UserException {
		User user = userDAO.get(userId);
		String tocken = httpHeaders.getRequestHeader("tocken").get(0);
		userDAO.deleteSession(user.getEmailId(), tocken);
	}
	
	private String generateTocken() {
		return UUID.randomUUID().toString();
		
	}

}
