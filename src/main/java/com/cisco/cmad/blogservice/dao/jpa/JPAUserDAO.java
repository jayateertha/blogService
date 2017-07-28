package com.cisco.cmad.blogservice.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.Session;
import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.dao.api.UserDAO;

public class JPAUserDAO implements UserDAO {

	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("blogService");

	@Override
	public User create(User user) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
		em.close();
		return user;
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isExists(String userName) {
		EntityManager em = factory.createEntityManager();
		User user = em.find(User.class, userName);
		em.close();
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public User get(String userName) {
		EntityManager em = factory.createEntityManager();
		User user = em.find(User.class, userName);
		em.close();
		return user;
	}

	@Override
	public Session getSession(String userName) {
		EntityManager em = factory.createEntityManager();
		Session session = em.find(Session.class, userName);
		em.close();
		return session;
	}

	@Override
	public boolean createSession(Session session) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(session);
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public boolean isValid(Credentials credentials) {
		EntityManager em = factory.createEntityManager();
		Credentials creds = em.find(Credentials.class, credentials.getUsername());
		em.close();
		if (creds.getPassword().equals(credentials.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteSession(String userId) {
		EntityManager em = factory.createEntityManager();
		Session session = (Session) em.find(Session.class, userId);
		if (session != null) {
			em.getTransaction().begin();
			em.remove(session);
			em.getTransaction().commit();
		}
		em.close();
		return true;
	}

}
