package org.ClAssignateur.domain.salles;

import java.util.Collection;

public interface SallesEntrepot {

	public void persister(Salle salle);

	public Collection<Salle> obtenirSalles();

}