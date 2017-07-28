package com.cisco.cmad.blogservice.api;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Credentials {
	@Id
	private String emailId;
	private String password;
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
