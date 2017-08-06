package com.cisco.cmad.blogservice.impl;

import java.util.Date;
import java.util.List;

import com.cisco.cmad.blogservice.api.Blog;
import com.cisco.cmad.blogservice.api.BlogException;
import com.cisco.cmad.blogservice.api.BlogManager;
import com.cisco.cmad.blogservice.api.BlogNotFoundException;
import com.cisco.cmad.blogservice.api.DuplicateBlogException;
import com.cisco.cmad.blogservice.api.InvalidBlogException;
import com.cisco.cmad.blogservice.api.NotAuthorizedException;
import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.api.UserManager;
import com.cisco.cmad.blogservice.dao.api.BlogDAO;
import com.cisco.cmad.blogservice.dao.jpa.JPABlogDAO;

public class BlogManagerImpl implements BlogManager {

	 BlogDAO blogDAO = new JPABlogDAO();
	 UserManager userManager = new UserManagerImpl();

	@Override
	public Blog createBlog(String userId, String tocken, Blog blog) throws DuplicateBlogException, InvalidBlogException, BlogException {
		if (blog == null) {
			throw new InvalidBlogException();
		}
		if (((blog.getData() == null) || (blog.getData().trim().isEmpty()))
				|| ((blog.getName() == null) || (blog.getName().trim().isEmpty()))) {
			throw new InvalidBlogException();
		}
		Blog createdBlog = null;
		try {
			User user = userManager.getUser(tocken, userId);
			if (user == null) {
				throw new NotAuthorizedException();
			}
			blog.setCreated(new Date());
			blog.setLastModifed(new Date());
			blog.setUser(user);
			createdBlog = blogDAO.create(blog);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BlogException();
		}

		return createdBlog;
	}

	@Override
	public Blog getBlog(int blogId) throws BlogNotFoundException, BlogException {
		Blog blog = null;
		try {
			blog = blogDAO.get(blogId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BlogException();
		}
		
		
		return blog;
	}

	@Override
	public Blog updateBlog(String userId, String tocken, Blog blog)
			throws BlogNotFoundException, NotAuthorizedException, InvalidBlogException, BlogException {
		if (blog == null) {
			throw new InvalidBlogException();
		}
		if (((blog.getData() == null) || (blog.getData().trim().isEmpty()))
				|| ((blog.getName() == null) || (blog.getName().trim().isEmpty()))) {
			throw new InvalidBlogException();
		}
		Blog existingBlog = blogDAO.get(blog.getBlogId());
		if(existingBlog == null) {
			throw new BlogNotFoundException();
		}
		Blog updatedBlog = null;
		User user = userManager.getUser(tocken, userId);
		if (user == null) {
			throw new NotAuthorizedException();
		}
		try {
			existingBlog.setLastModifed(new Date());
			existingBlog.setData(blog.getData());
			existingBlog.setName(blog.getName());
			updatedBlog = blogDAO.update(existingBlog);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new BlogException();
		}

		return updatedBlog;
	}

	@Override
	public void deleteBlog(String userId, String tocken, int blogId) throws BlogNotFoundException, NotAuthorizedException, BlogException {
		try {
			Blog blog = blogDAO.get(blogId);
			if(blog == null) {
				throw new BlogNotFoundException();
			}
			User user = userManager.getUser(tocken, userId);
			if (user == null) {
				throw new NotAuthorizedException();
			}
			blogDAO.delete(blog);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BlogException();
		}
		
	}

	@Override
	public List<Blog> getBlogs(String blogFilter, int index, int count) throws BlogException {
		List<Blog> blogs = null;
		try {
			blogs = blogDAO.getMultiple(blogFilter, index, count);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BlogException();
		}
		return blogs;
	}

}
