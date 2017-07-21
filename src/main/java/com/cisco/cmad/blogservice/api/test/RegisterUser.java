package com.cisco.cmad.blogservice.api.test;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.Test;

import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.api.UserManager;
import com.cisco.cmad.blogservice.impl.UserManagerImpl;

public class RegisterUser {

	@Test
	public void test() {
		
		UserManager userManager = new UserManagerImpl();
		User user = new User();
		user.setName("Test User");
		user.setEmailId("test@opendns.com");
		user.setContactNo("9902257343");
		Response response = userManager.registerUser(user);
		if (response.getStatus() == 200) {
			fail("User creation failed");
		}
	}

}
