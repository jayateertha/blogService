package com.cisco.cmad.blogservice.api.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.api.UserManager;
import com.cisco.cmad.blogservice.impl.UserManagerImpl;

public class RegisterUser {

	@Test
	public void test() {
		
		UserManager userManager = new UserManagerImpl();
		User user = new User();
		user.setUserId(1234);
		user.setName("Test User");
		user.setEmailId("test@opendns.com");
		user.setContactNo("9902257343");
		User createdUser = userManager.registerUser(user);
		if (createdUser == null) {
			fail("User creation failed");
		}
	}

}
