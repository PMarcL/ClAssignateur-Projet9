package org.ClAssignateur.domain;

import java.util.Optional;

public interface EntrepotSalles {

	public void ajouterSalle(Salle salle);

	public Optional<Salle> obtenirSalleRepondantDemande(Demande demande);

}