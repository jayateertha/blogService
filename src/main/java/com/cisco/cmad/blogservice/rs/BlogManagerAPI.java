package com.cisco.cmad.blogservice.rs;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.blogservice.api.Blog;
import com.cisco.cmad.blogservice.api.BlogException;
import com.cisco.cmad.blogservice.api.BlogManager;
import com.cisco.cmad.blogservice.api.BlogNotFoundException;
import com.cisco.cmad.blogservice.api.InvalidBlogException;
import com.cisco.cmad.blogservice.impl.BlogManagerImpl;

@Path("/blog")
public class BlogManagerAPI {

	BlogManager blogManager = new BlogManagerImpl();

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createBlog(Blog blog) {
		Blog createdBlog = null;
		try {
			createdBlog = blogManager.createBlog(blog);
		} catch (InvalidBlogException ib3) {
			return Response.status(422).build();
		} catch (BlogException be) {
			return Response.status(500).build();
		}

		return Response.status(201).entity(createdBlog).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{blogId}")
	public Response getBlog(@PathParam("blogId") int blogId) {
		Blog blog = null;
		try {
			blog = blogManager.getBlog(blogId);
		} catch (BlogNotFoundException bnf) {
			return Response.status(404).build();
		} catch (BlogException be) {
			return Response.status(500).build();
		}
		return Response.status(200).entity(blog).build();
	}

	@PUT
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateBlog(Blog blog) {
		Blog updatedBlog = null;
		try {
			updatedBlog = blogManager.updateBlog(blog);
		} catch (BlogNotFoundException bnf) {
			return Response.status(404).build();
		} catch (BlogException be) {
			return Response.status(500).build();
		}
		return Response.status(200).entity(updatedBlog).build();
	}

	@DELETE
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Path("/{blogId}")
	public Response deleteBlog(@PathParam("blogId") int blogId) {
		try {
			blogManager.deleteBlog(blogId);
		} catch (BlogNotFoundException bnf) {
			return Response.status(404).build();
		} catch (BlogException be) {
			return Response.status(500).build();
		}
		return Response.status(200).build();

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBlogs(@QueryParam("blogFilter") String blogFilter, @QueryParam("index") int index, @QueryParam("count") int count) {
		List<Blog> blogs = null;
		try {
			blogs = blogManager.getBlogs(blogFilter, index, count);
			
		} catch (BlogException be) {
			be.printStackTrace();
			return Response.status(500).build();
		}
		return Response.status(200).entity(blogs).build();
	}

}
