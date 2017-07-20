package com.cisco.cmad.blogservice.dao.api;

import com.cisco.cmad.blogservice.api.Comment;

public interface CommentDAO {
	public Comment create(Comment comment);
	public Comment get(int commentId);
	public Comment update(Comment comment);
	public void delete(int commentId);
	public Comment[] getMultiple(String filter, int index, int count);
}
