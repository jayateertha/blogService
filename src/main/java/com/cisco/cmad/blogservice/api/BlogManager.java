package com.cisco.cmad.blogservice.api;

import java.util.List;

public interface BlogManager {

	public Blog createBlog(Blog blog) throws DuplicateBlogException, InvalidBlogException, BlogException;

	public Blog getBlog(int blogId) throws BlogNotFoundException, BlogException;

	public Blog updateBlog(Blog blog) throws BlogNotFoundException, NotAuthorizedException, InvalidBlogException, BlogException;

	public void deleteBlog(int blogId) throws BlogNotFoundException, NotAuthorizedException, BlogException;

	public List<Blog> getBlogs(String blogFilter, int index, int count) throws BlogException;

}
