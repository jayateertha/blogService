package com.cisco.cmad.blogservice.api;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(
	    name="findAllBlogs",
	    query="SELECT blog FROM Blog blog order by blog.blogId desc"
	)
public class Blog {
	@Id
	@GeneratedValue
	private int blogId;
	
	@Column(nullable=false)
	private String name;
	
	private String Category;
	
	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	private String data;
	
	@ManyToOne
	@JoinColumn(name="emailId", nullable=false)
	private User user;
	
	@OneToMany
	@JoinColumn(name="commentId", nullable=true)
	private List<Comment> comments;
	
	@Column(updatable = false, nullable=false)
	private Date created;
	
	@Column(nullable=false)
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
