package org.ClAssignateur.domain;

import org.ClAssignateur.domain.salles.Salle;

import org.ClAssignateur.domain.demandes.Demande;
import java.util.Collection;
import java.util.Optional;

public interface SelectionSalleStrategie {

	public Optional<Salle> selectionnerSalle(Collection<Salle> salles, Demande demande);
}
