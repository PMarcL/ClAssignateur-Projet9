package org.ClAssignateur.domain;

import java.util.Collection;

public interface SallesEntrepot {

	public void persister(Salle salle);

	public Collection<Salle> obtenirSalles();

}