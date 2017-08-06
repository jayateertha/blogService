package com.cisco.cmad.blogservice.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.cisco.cmad.blogservice.api.Comment;
import com.cisco.cmad.blogservice.dao.api.CommentDAO;

public class JPACommentDAO implements CommentDAO {

	private EntityManagerFactory factory = JPAEntityManager.getInstance();
	
	@Override
	public Comment create(Comment comment) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(comment);
		em.getTransaction().commit();
		em.close();
		return comment;
	}

	@Override
	public Comment get(int commentId) {
		EntityManager em = factory.createEntityManager();
		Comment comment = em.find(Comment.class, commentId);
		em.close();
		return comment;
	}


	@Override
	public void delete(int commentId) {
		EntityManager em = factory.createEntityManager();
		Comment existingComment = (Comment) em.find(Comment.class, commentId);
		if (existingComment != null) {
			em.getTransaction().begin();
			em.remove(existingComment);
			em.getTransaction().commit();
		}
		em.close();
		
	}
	
}
