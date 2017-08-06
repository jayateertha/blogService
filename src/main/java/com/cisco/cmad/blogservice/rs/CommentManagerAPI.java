package com.cisco.cmad.blogservice.rs;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.blogservice.api.Comment;
import com.cisco.cmad.blogservice.api.CommentException;
import com.cisco.cmad.blogservice.api.CommentManager;
import com.cisco.cmad.blogservice.api.InvalidCommentException;
import com.cisco.cmad.blogservice.impl.CommentManagerImpl;

@Path("/comment")
public class CommentManagerAPI {
	CommentManager commentManager = new CommentManagerImpl();

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addComment(@Context HttpHeaders httpHeaders, Comment comment) {

		if (httpHeaders.getRequestHeader("tocken") == null) {
			return Response.status(401).build();
		}
		String tocken = httpHeaders.getRequestHeader("tocken").get(0);

		if (httpHeaders.getRequestHeader("user") == null) {
			return Response.status(401).build();
		}
		String userId = httpHeaders.getRequestHeader("user").get(0);
		Comment addedComment = null;
		try {
			commentManager.addComment(userId, tocken, comment);
		} catch (InvalidCommentException ice) {
			return Response.status(422).build();
		} catch (CommentException ce) {
			return Response.status(500).build();
		}
		return Response.status(201).entity(addedComment).build();
	}

}
