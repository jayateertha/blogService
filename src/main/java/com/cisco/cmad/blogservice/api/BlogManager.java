package com.cisco.cmad.blogservice.api;

import java.util.List;

public interface BlogManager {

	public Blog createBlog(String userId, String tocken, Blog blog) throws DuplicateBlogException, InvalidBlogException, BlogException;

	public Blog getBlog(int blogId) throws BlogNotFoundException, BlogException;

	public Blog updateBlog(String userId, String tocken, Blog blog) throws BlogNotFoundException, NotAuthorizedException, InvalidBlogException, BlogException;

	public void deleteBlog(String userId, String tocken, int blogId) throws BlogNotFoundException, NotAuthorizedException, BlogException;

	public List<Blog> getBlogs(String blogFilter, int index, int count) throws BlogException;

}
