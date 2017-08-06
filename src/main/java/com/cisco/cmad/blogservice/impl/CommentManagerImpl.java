package com.cisco.cmad.blogservice.impl;

import java.util.Date;

import com.cisco.cmad.blogservice.api.Blog;
import com.cisco.cmad.blogservice.api.BlogNotFoundException;
import com.cisco.cmad.blogservice.api.Comment;
import com.cisco.cmad.blogservice.api.CommentException;
import com.cisco.cmad.blogservice.api.CommentManager;
import com.cisco.cmad.blogservice.api.InvalidBlogException;
import com.cisco.cmad.blogservice.api.InvalidCommentException;
import com.cisco.cmad.blogservice.api.NotAuthorizedException;
import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.api.UserManager;
import com.cisco.cmad.blogservice.dao.api.BlogDAO;
import com.cisco.cmad.blogservice.dao.api.CommentDAO;
import com.cisco.cmad.blogservice.dao.jpa.JPABlogDAO;
import com.cisco.cmad.blogservice.dao.jpa.JPACommentDAO;

public class CommentManagerImpl implements CommentManager{
	
	CommentDAO commentDAO = new JPACommentDAO();
	BlogDAO blogDAO = new JPABlogDAO();
	UserManager userManager = new UserManagerImpl();

	@Override
	public Comment addComment(String userId, String tocken, Comment comment) throws InvalidCommentException, CommentException {
		if (comment == null) {
			throw new InvalidCommentException();
		}
		if (((comment.getData() == null) || (comment.getData().trim().isEmpty()))) {
			throw new InvalidBlogException();
		}
		Blog blog = blogDAO.get(comment.getBlog().getBlogId());
		if(blog  == null) {
			throw new BlogNotFoundException();
		}
		
		User user = userManager.getUser(tocken, userId);
		if (user == null) {
			throw new NotAuthorizedException();
		}
		comment.setCreated(new Date());
		comment.setUser(user);
		comment.setBlog(blog);
		commentDAO.create(comment);
		return comment;
	}

}
