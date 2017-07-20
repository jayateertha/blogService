package com.cisco.cmad.blogservice.dao.api;

import com.cisco.cmad.blogservice.api.Blog;

public interface BlogDAO {
	public Blog create(Blog blog);
	public Blog get(int blogId);
	public Blog update(Blog blog);
	public void delete(int blogId);
	public Blog[] getMultiple(String filter, int index, int count);
	public int getCount();
	
}
