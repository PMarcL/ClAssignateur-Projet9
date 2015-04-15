package org.ClAssignateur.domaine.salles;

import java.util.Collection;

public interface SallesEntrepot {

	public void persister(Salle salle);

	public Collection<Salle> obtenirSalles();

}