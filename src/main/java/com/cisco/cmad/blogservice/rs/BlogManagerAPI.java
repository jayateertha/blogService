package com.cisco.cmad.blogservice.rs;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cisco.cmad.blogservice.api.Blog;
import com.cisco.cmad.blogservice.impl.BlogManagerImpl;

@Path("/blog")
public class BlogManagerAPI {

	BlogManagerImpl blogManagerImpl = new BlogManagerImpl();
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response createBlog(Blog blog) {
		Blog createdBlog = blogManagerImpl.createBlog(blog);
		return Response.status(200).entity(createdBlog).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getBlog(int blogId) {
		Blog blog = blogManagerImpl.getBlog(blogId);
		return Response.status(200).entity(blog).build();
	}

	
	public Response updateBlog(Blog blog) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Response deleteBlog(int blogId) {
		// TODO Auto-generated method stub
		return null;

	}

	
	public Response getBlogs(String blogFilter, int index, int count)  {
		// TODO Auto-generated method stub
		return null;
	}

	public Response getBlogCount()  {
		// TODO Auto-generated method stub
		return null;
	}

}
