package com.cisco.cmad.blogservice.dao.api;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.Session;
import com.cisco.cmad.blogservice.api.User;

public interface UserDAO {
	public User create(User user);
	public User get(String emailId);
	public User update(User user);
	public boolean isExists(String userName);
	public boolean createSession(Session session);
	public boolean isValid(Credentials credentials);
	public boolean deleteSession(String userId);
	public Session getSession(String emailId);
	
}
