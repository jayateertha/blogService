package com.cisco.cmad.blogservice.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.cisco.cmad.blogservice.api.Credentials;
import com.cisco.cmad.blogservice.api.User;
import com.cisco.cmad.blogservice.dao.api.UserDAO;

public class JPAUserDAO implements UserDAO {

	private EntityManagerFactory factory = JPAEntityManager.getInstance();

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
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(user);
		em.getTransaction().commit();
		em.close();
		return user;
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
	public boolean isValid(Credentials credentials) {
		EntityManager em = factory.createEntityManager();
		Credentials creds = em.find(Credentials.class, credentials.getEmailId());
		em.close();
		if (creds.getPassword().equals(credentials.getPassword())) {
			return true;
		}
		return false;
	}

}
