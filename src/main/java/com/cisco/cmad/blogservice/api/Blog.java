package com.cisco.cmad.blogservice.api;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cascade;


public class Blog {
	@Id
	private int blogId;
	private String name;
	private String data;
	
	
	private User user;
//	 @ElementCollection(targetClass=Comment.class)
	private List<Comment> comments;
	private Date created;
	private Date lastModifed;
	
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	//@OneToMany(targetEntity=Comment.class, fetch = FetchType.LAZY)
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getLastModifed() {
		return lastModifed;
	}
	public void setLastModifed(Date lastModifed) {
		this.lastModifed = lastModifed;
	}
	
	
}
