package org.ClAssignateur.domain;

import java.util.Optional;

public interface EntrepotSalles {

	public void persister(Salle salle);

	public Optional<Salle> obtenirSalleRepondantDemande(StrategieDeSelectionDeSalle strategie, Demande demande);

}