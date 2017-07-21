package com.cisco.cmad.blogservice.api;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Session {
	@Id
	private String userName;
	private String tocken;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTocken() {
		return tocken;
	}
	public void setTocken(String tocken) {
		this.tocken = tocken;
	}
	
}
