package com.cisco.cmad.blogservice.dao.api;

import com.cisco.cmad.blogservice.api.Comment;

public interface CommentDAO {
	public Comment create(Comment comment);
	public Comment get(int commentId);
	public void delete(int commentId);
}
