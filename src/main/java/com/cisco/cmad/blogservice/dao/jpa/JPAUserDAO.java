package com.cisco.cmad.blogservice.dao.jpa;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.dao.api.UserDAO;

public class JPAUserDAO implements UserDAO {

	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExists(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User get(String emailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createSession(String userName, String tocken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid(Credentials credentials) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteSession(String userName, String tocken) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
