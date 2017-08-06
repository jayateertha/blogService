package com.cisco.cmad.blogservice.api;

public class Session {
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
