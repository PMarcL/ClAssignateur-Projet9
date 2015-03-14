package org.ClAssignateur.domain;

import java.util.Collection;

public interface EntrepotSalles {

	public void persister(Salle salle);

	public Collection<Salle> obtenirSalles();

}