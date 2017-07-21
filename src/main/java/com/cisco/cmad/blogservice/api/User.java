package com.cisco.cmad.blogservice.api;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	@Id
	private String emailId;
	private String name;
	private String contactNo;
	//private List<Blog> blogs;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
/*	@OneToMany(fetch = FetchType.LAZY)
	public List<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}*/
	
	
	
}
