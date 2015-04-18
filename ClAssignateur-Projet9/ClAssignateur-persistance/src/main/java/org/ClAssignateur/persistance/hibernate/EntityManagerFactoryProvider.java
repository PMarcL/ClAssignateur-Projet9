package org.ClAssignateur.persistance.hibernate;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryProvider {

	private static EntityManagerFactory instance;

	public static EntityManagerFactory getFactory() {
		if (instance == null) {
			instance = Persistence.createEntityManagerFactory("ClAssignateur-Projet9");
		}
		return instance;
	}

}