package org.ClAssignateur.persistance.hibernate;

import javax.persistence.EntityManager;

public class EntityManagerProvider {

	private static ThreadLocal<EntityManager> localEntityManager = new ThreadLocal<>();

	public EntityManager getEntityManager() {
		return localEntityManager.get();
	}

	public static void setEntityManager(EntityManager entityManager) {
		localEntityManager.set(entityManager);
	}

	public static void clearEntityManager() {
		localEntityManager = new ThreadLocal<>();
	}
}