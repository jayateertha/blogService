package com.cisco.cmad.blogservice.dao.api;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.User;

public interface UserDAO {
	public User create(User user);
	public User get(String emailId);
	public User update(User user);
	public boolean isExists(String userName);
	public boolean isValid(Credentials credentials);
	
}
