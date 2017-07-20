package com.cisco.cmad.blogservice.dao.api;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.User;

public interface UserDAO {
	public User create(User user);
	public User get(int userId);
	public User get(String emailId);
	public User update(User user);
	public boolean isExists(User user);
	public boolean createSession(String userName, String tocken);
	public boolean isValid(Credentials credentials);
	public boolean deleteSession(String userName, String tocken);
	
}
