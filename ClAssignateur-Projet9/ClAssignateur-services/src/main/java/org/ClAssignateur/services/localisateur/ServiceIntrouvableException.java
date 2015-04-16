package org.ClAssignateur.services.localisateur;

public class ServiceIntrouvableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceIntrouvableException(Class<?> service) {
		super("Le service " + service.getCanonicalName() + "n'a pas été enregistré.");
	}

}
