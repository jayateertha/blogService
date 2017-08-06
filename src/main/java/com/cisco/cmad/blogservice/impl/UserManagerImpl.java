package com.cisco.cmad.blogservice.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

	static UserDAO userDAO = new JPAUserDAO();
	static Map<String, Session> sessionMap = new HashMap<String, Session>();

	@Override
	public User register(User user) throws DuplicateUserException, InvalidUserException, UserException {
		if ((user.getName() == null) || user.getName().trim().equals("") || (user.getEmailId() == null)
				|| user.getEmailId().trim().equals("")) {
			throw new InvalidUserException();
		}

		if (userDAO.isExists(user.getEmailId())) {
			throw new DuplicateUserException();
		}
		User createdUser = null;
		try {
			createdUser = userDAO.create(user);
		} catch (Exception e) {
			throw new UserException();
		}

		return createdUser;

	}

	@Override
	public Session login(Credentials credentials) throws InvalidUserException, UserException {
		try {

			if (!userDAO.isExists(credentials.getEmailId())) {
				System.out.println("User Does not exists");
				throw new InvalidUserException();
			}

			Session session = null;
			if (userDAO.isValid(credentials)) {
				String tocken = generateTocken();
				session = new Session();
				session.setTocken(tocken);
				session.setUserName(credentials.getEmailId());
				UserManagerImpl.sessionMap.put(tocken, session);
				return session;
			} else {
				System.out.println("Auth failed");
				throw new InvalidUserException();
			}
		} catch (Exception e) {
			throw new UserException();
		}

	}

	@Override
	public void logout(String tocken, Credentials credentials)
			throws InvalidUserException, NotAuthorizedException, UserException {
		try {
			Session session = UserManagerImpl.sessionMap.get(tocken);
			if ((session == null) || (!session.getTocken().equals(tocken))) {
				throw new NotAuthorizedException();
			}
			if (credentials.getEmailId().isEmpty()) {
				throw new InvalidUserException();
			}
			UserManagerImpl.sessionMap.remove(tocken);
		} catch (Exception e) {
			throw new UserException();
		}

	}

	private String generateTocken() {
		return UUID.randomUUID().toString();

	}

	@Override
	public User getUser(String tocken, String userId)
			throws UserNotFoundException, NotAuthorizedException, UserException {
		User user = null;
		try {
			System.out.println("userId:" + userId);
			Session session = UserManagerImpl.sessionMap.get(tocken);
			System.out.println("tocken:" + tocken);
			System.out.println("Session:" + session);
			if ((session == null) || (!session.getTocken().equals(tocken))) {
				throw new NotAuthorizedException();
			}
			user = userDAO.get(userId);
			if (user == null) {
				throw new UserNotFoundException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException();
		}

		return user;
	}

	@Override
	public User updateUser(String tocken, User user)
			throws UserNotFoundException, NotAuthorizedException, InvalidUserException, UserException {
		if (user.getContactNo().trim().isEmpty() || user.getEmailId().trim().isEmpty()) {
			throw new InvalidUserException();
		}
		Session session = UserManagerImpl.sessionMap.get(tocken);
		if ((session == null) || (!session.getTocken().equals(tocken))) {
			throw new NotAuthorizedException();
		}
		User existingUser = userDAO.get(user.getEmailId());
		if (existingUser == null) {
			throw new UserNotFoundException();
		}
		try {
			existingUser.setContactNo(user.getContactNo());
			userDAO.update(user);
		} catch (Exception e) {
			throw new UserException();
		}
		
		return user;
	}

}
