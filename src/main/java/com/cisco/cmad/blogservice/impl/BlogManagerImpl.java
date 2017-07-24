package com.cisco.cmad.blogservice.impl;

import com.cisco.cmad.blogservice.api.Blog;
import com.cisco.cmad.blogservice.api.BlogException;
import com.cisco.cmad.blogservice.api.BlogManager;
import com.cisco.cmad.blogservice.api.BlogNotFoundException;
import com.cisco.cmad.blogservice.api.DuplicateBlogException;
import com.cisco.cmad.blogservice.api.InvalidBlogException;
import com.cisco.cmad.blogservice.api.NotAuthorizedException;
import com.cisco.cmad.blogservice.dao.api.BlogDAO;
import com.cisco.cmad.blogservice.dao.jpa.JPABlogDAO;

public class BlogManagerImpl implements BlogManager {

	BlogDAO blogDAO = new JPABlogDAO();
	
	@Override
	public Blog createBlog(Blog blog) throws DuplicateBlogException, InvalidBlogException, BlogException {
		Blog createdBlog = blogDAO.create(blog);
		return createdBlog;
	}

	@Override
	public Blog getBlog(int blogId) throws BlogNotFoundException, BlogException {
		Blog blog = blogDAO.get(blogId);
		return blog;
	}

	@Override
	public Blog updateBlog(Blog blog)
			throws BlogNotFoundException, NotAuthorizedException, InvalidBlogException, BlogException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBlog(int blogId) throws BlogNotFoundException, NotAuthorizedException, BlogException {
		// TODO Auto-generated method stub

	}

	@Override
	public Blog[] getBlogs(String blogFilter, int index, int count) throws BlogException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBlogCount() throws BlogException {
		// TODO Auto-generated method stub
		return 0;
	}

}
