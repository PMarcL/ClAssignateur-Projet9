package org.ClAssignateur.services.localisateur;

public class ServiceDoublonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceDoublonException(Class<?> service) {
		super("Le service " + service.getCanonicalName() + " a déjà été enregistré.");
	}

}
