package com.cisco.cmad.blogservice.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cisco.cmad.blogservice.api.Blog;
import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.dao.api.BlogDAO;

public class JPABlogDAO implements BlogDAO {
	
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("blogService");
	
	@Override
	public Blog create(Blog blog) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(blog);
		em.getTransaction().commit();
		em.close();
		return blog;
	}

	@Override
	public Blog get(int blogId) {
		EntityManager em = factory.createEntityManager();
		Blog blog = em.find(Blog.class, blogId);
		em.close();
		return blog;
	}

	@Override
	public Blog update(Blog blog) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int blogId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Blog[] getMultiple(String filter, int index, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
