package com.cisco.cmad.blogservice.api;

public interface CommentManager {
	public Comment addComment(Comment comment) throws InvalidCommentException, CommentException;

	public Comment getComment(int commentId) throws CommentNotFoundException, CommentException;

	public Comment updateComment(Comment comment)
			throws CommentNotFoundException, InvalidCommentException, NotAuthorizedException, CommentException;

	public void deleteComment(int CommentId) throws CommentNotFoundException, NotAuthorizedException, CommentException;

	public Comment[] getComments(int blogId, int index, int count) throws CommentException;
}
