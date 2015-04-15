package org.ClAssignateur.domaine.assignateur.strategies;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.salles.Salle;

import java.util.Collection;
import java.util.Optional;

public interface SelectionSalleStrategie {

	public Optional<Salle> selectionnerSalle(Collection<Salle> salles, Demande demande);
}
