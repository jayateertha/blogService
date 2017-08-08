package com.cisco.cmad.blogservice.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import com.cisco.cmad.blogservice.api.Blog;
import com.cisco.cmad.blogservice.dao.api.BlogDAO;

public class JPABlogDAO implements BlogDAO {

	private EntityManagerFactory factory = JPAEntityManager.getInstance();

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
		EntityManager em = factory.createEntityManager();
		Blog existingBlog = em.find(Blog.class, blog.getBlogId());
		if (existingBlog != null) {
			em.getTransaction().begin();
			existingBlog = em.find(Blog.class, blog.getBlogId());
			em.remove(existingBlog);
			em.getTransaction().commit();
		}
		em.close();
	}

	@Override
	public List<Blog> getMultiple(String filter, int index, int count) {
		List<Blog> resultBLog = null;
		boolean needMoreBlogs = false;
		int blogCount = 0;
		if ((filter == null) || filter.trim().isEmpty()) {
			System.out.println("GetMultiple: index:");
			resultBLog = queryBlogs(index, count);
		} else {
			resultBLog = new ArrayList<Blog>();
			do {
				List<Blog> tempList = queryBlogs(index, count);
				for (Blog blog : tempList) {
					if (blog.getName().contains(filter)) {
						resultBLog.add(blog);
						blogCount++;
						if ((blogCount == count)) {
							needMoreBlogs = false;
							break;
						}
					}
				}

			} while (needMoreBlogs);
		}
		return resultBLog;
	}

	private List<Blog> queryBlogs(int index, int count) {
		List<Blog> blogs = null;
		int startCount = count * index;
		if (startCount < 0) {
			startCount = 0;
		}
		if (count < 0) {
			count = 0;
		}
		EntityManager em = factory.createEntityManager();

		blogs = em.createNamedQuery("findAllBlogs", Blog.class).setMaxResults(startCount + count)
				.setFirstResult(startCount).getResultList();
		return blogs;
	}
	
    @Override
    // Search blogs by category
    // @pageNum - 0 based page index
    public List<Blog> readByCategory(String category, int pageNum) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Blog> tquery = em.createNamedQuery(Blog.FIND_BY_CATEGORY, Blog.class);
        tquery.setParameter("category", category);
        setPageParams(tquery, pageNum);
        List<Blog> blogs = tquery.getResultList();
        em.getTransaction().commit();
        em.close();
        return blogs;
    }

    @Override
    // Read all blogs
    // @pageNum - 0 based page index
    public List<Blog> readAllBlogs(int pageNum) {
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        TypedQuery<Blog> tquery = em.createNamedQuery(Blog.FIND_ALL, Blog.class);
        setPageParams(tquery, pageNum);
        List<Blog> blogs = tquery.getResultList();
        em.getTransaction().commit();
        em.close();
        return blogs;
    }
    
    private void setPageParams(TypedQuery<Blog> tquery, int pageNum) {
        tquery.setMaxResults(5);
        int index = pageNum * 5;
        tquery.setFirstResult(index);
    }



}
