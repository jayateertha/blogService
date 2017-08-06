package com.cisco.cmad.blogservice.api;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	private int commentId;
	
	private String data;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="emailId", nullable=false)
	private User user;
	
	@Column(updatable = false, nullable=false)
	private Date created;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="blogId", nullable=false)
	private Blog blog;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}
}
