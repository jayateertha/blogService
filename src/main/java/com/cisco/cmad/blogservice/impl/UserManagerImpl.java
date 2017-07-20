package com.cisco.cmad.blogservice.impl;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.DuplicateUserException;
import com.cisco.cmad.blogservice.api.InvalidUserException;
import com.cisco.cmad.blogservice.api.NotAuthorizedException;
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
		
		if(userDAO.isExists(user)) {
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
	public User login(Credentials credentials) throws InvalidUserException, UserException {
		
		return null;
	}

	@Override
	public User logout(int userId) throws InvalidUserException, UserException {
		// TODO Auto-generated method stub
		return null;
	}

}
