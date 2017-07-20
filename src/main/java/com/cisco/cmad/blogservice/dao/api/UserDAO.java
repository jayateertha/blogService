package com.cisco.cmad.blogservice.dao.api;

import com.cisco.cmad.blogservice.api.User;

public interface UserDAO {
	public User create(User user);
	public User get(int userId);
	public User update(User user);
	public boolean isExists(User user);
	
}
