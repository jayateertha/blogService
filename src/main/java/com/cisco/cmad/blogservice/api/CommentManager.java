package com.cisco.cmad.blogservice.api;

public interface CommentManager {
	public Comment addComment(String userId, String tocken, Comment comment) throws InvalidCommentException, CommentException;

	//public Comment getComment(int commentId) throws CommentNotFoundException, CommentException;

}
