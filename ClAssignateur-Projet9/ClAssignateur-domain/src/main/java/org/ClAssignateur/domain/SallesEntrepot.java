package org.ClAssignateur.domain;

import java.util.Optional;

public interface SallesEntrepot {

	public void ajouterSalle(Salle salle);

	public Optional<Salle> obtenirSalleRepondantDemande(Demande demande);

}