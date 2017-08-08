package com.cisco.cmad.blogservice.api;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.UpdateTimestamp;

import com.cisco.cmad.blogservice.api.Blog;

@Entity
@NamedQueries({ @NamedQuery(name = Blog.FIND_ALL, query = "SELECT b FROM Blog b ORDER BY b.lastUpdatedOn DESC"),
    @NamedQuery(name = Blog.COUNT_ALL, query = "SELECT COUNT(b) FROM Blog b"),
    @NamedQuery(name = Blog.FIND_BY_CATEGORY, query = "SELECT b FROM Blog b WHERE b.category = :category ORDER BY b.lastUpdatedOn DESC"),
    @NamedQuery(name = Blog.DELETE_BLOG_COMMENTS, query = "DELETE FROM Comment c WHERE c.blog.blogId = :blogId"),
    @NamedQuery(name = Blog.FIND_USER_BLOGS, query = "SELECT b FROM Blog b WHERE b.user.emailId = :emailId") })

//@NamedQuery(name = "findAllBlogs", query = "SELECT b FROM Blog b order by b.blogId desc")
public class Blog {
    public static final String FIND_ALL = "Blog.findAll";
    public static final String COUNT_ALL = "Blog.countAll";
    public static final String FIND_BY_CATEGORY = "Blog.findByCategory";
    public static final String DELETE_BLOG_COMMENTS = "Blog.deleteBlogComments";
    public static final String FIND_USER_BLOGS = "Blog.findUserBlogs";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int blogId;

	@Column(nullable = false)
	private String name;

	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	private String data;

	@ManyToOne(cascade = { CascadeType.REFRESH })
	//@JoinColumn(name = "emailId", nullable = false)
	private User user;

/*	@OneToMany
	@JoinColumn(name = "commentId", nullable = true)
	private List<Comment> comments;

*/	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastUpdatedOn;

	/*
	 * @Column(updatable = false, nullable=false) private Date created;
	 * 
	 * 
	 * @Column(nullable=false) private Date lastModifed;
	 * 
	 */
	public int getBlogId() {
		return blogId;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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

/*	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
*/
	/*
	 * public Date getCreated() { return created; }
	 * 
	 * public void setCreated(Date created) { this.created = created; }
	 * 
	 * public Date getLastModifed() { return lastModifed; }
	 * 
	 * public void setLastModifed(Date lastModifed) { this.lastModifed =
	 * lastModifed; }
	 */
	}
