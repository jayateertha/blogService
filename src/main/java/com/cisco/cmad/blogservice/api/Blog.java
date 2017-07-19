package com.cisco.cmad.blogService.api;

import java.util.Date;
import java.util.List;

public class Blog {
	private int blogId;
	private String name;
	private String data;
	private User user;
	private List<Comment> comments;
	private Date created;
	private Date lastModifed;
	
}
