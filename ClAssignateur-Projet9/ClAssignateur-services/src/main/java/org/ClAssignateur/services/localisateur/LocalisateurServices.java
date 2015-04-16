package org.ClAssignateur.services.localisateur;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LocalisateurServices {

	private static LocalisateurServices instance;
	private static final ReentrantLock verrou = new ReentrantLock();

	private HashMap<Class<?>, Object> services;

	public static LocalisateurServices getInstance() {
		if (instance == null) {
			verrou.lock();
			try {
				if (instance == null) {
					instance = new LocalisateurServices();
				}
			} finally {
				verrou.unlock();
			}
		}
		return instance;
	}

	public void remettreAZero() {
		verrou.lock();
		try {
			instance = null;
		} finally {
			verrou.unlock();
		}
	}

	private LocalisateurServices() {
		services = new HashMap<Class<?>, Object>();
	}

	@SuppressWarnings("unchecked")
	public <T> T obtenir(Class<T> service) {
		if (!services.containsKey(service)) {
			throw new ServiceIntrouvableException(service);
		}

		return (T) services.get(service);

	}

	public <T> void enregistrer(Class<T> service, T implementation) {
		if (services.containsKey(service)) {
			throw new ServiceDoublonException(service);
		}
		services.put(service, implementation);
	}
}
