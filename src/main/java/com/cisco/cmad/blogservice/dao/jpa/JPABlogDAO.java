package com.cisco.cmad.blogservice.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.cisco.cmad.blogservice.api.Blog;
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
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(blog);
		em.getTransaction().commit();
		em.close();
		return blog;
	}

	@Override
	public void delete(Blog blog) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Blog> getMultiple(String filter, int index, int count) {
		int startCount = count * (index - 1);
		if (startCount < 0) {
			startCount = 0;
		}
		if (count < 0) {
			count = 0;
		}
		EntityManager em = factory.createEntityManager();
		List<Blog> blogs = null;
		blogs = em.createNamedQuery("findAllBlogs", Blog.class).setMaxResults(startCount + count)
				.setFirstResult(startCount).getResultList();
		return blogs;
	}

}
