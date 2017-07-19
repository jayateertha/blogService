package com.cisco.cmad.blogService.api;

public interface BlogManager {

	public Blog createBlog(Blog blog) throws DuplicateBlogException, InvalidBlogException, BlogException;

	public Blog getBlog(int blogId) throws BlogNotFoundException, BlogException;

	public Blog updateBlog(Blog blog) throws BlogNotFoundException, NotAuthorizedException, InvalidBlogException, BlogException;

	public void deleteBlog(int blogId) throws BlogNotFoundException, NotAuthorizedException, BlogException;

	public Blog[] getBlogs(String blogFilter, int index, int count) throws BlogException;
	
	public int getBlogCount() throws BlogException;

}
