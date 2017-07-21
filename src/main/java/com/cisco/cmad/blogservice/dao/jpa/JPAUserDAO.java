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
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	public User get(String userName) {
		EntityManager em = factory.createEntityManager();
		User user = em.find(User.class, userName);
		return user;
	}

	@Override
	public boolean createSession(String userName, String tocken) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid(Credentials credentials) {
		EntityManager em = factory.createEntityManager();
		Credentials creds = em.find(Credentials.class, credentials.getUsername());
		if (creds.getPassword().equals(credentials.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public boolean deleteSession(String userName, String tocken) {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.remove(getSession(userName));
		em.getTransaction().commit();
		em.close();
		return true;
	}

	@Override
	public Session getSession(String userName) {
		EntityManager em = factory.createEntityManager();
		Session session = em.find(Session.class, userName);
		return session;
	}

}
