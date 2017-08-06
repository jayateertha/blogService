package com.cisco.cmad.blogservice.dao.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAEntityManager {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("blogService");
	private JPAEntityManager() {
		
	}
	
	public static EntityManagerFactory getInstance() {
		return factory;
	}
}
