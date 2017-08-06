package com.cisco.cmad.blogservice.dao.api;

import java.util.List;

import com.cisco.cmad.blogservice.api.Blog;

public interface BlogDAO {
	public Blog create(Blog blog);
	public Blog get(int blogId);
	public Blog update(Blog blog);
	public void delete(Blog blog);
	public List<Blog> getMultiple(String filter, int index, int count);
	
}
