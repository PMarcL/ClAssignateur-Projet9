package org.ClAssignateur.domaine.assignateur.strategies;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.salles.Salle;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class SelectionSalleOptimaleStrategie implements SelectionSalleStrategie {

	@Override
	public Optional<Salle> selectionnerSalle(Collection<Salle> salles, Demande demande) {
		int nbParticipants = demande.getNbParticipants();
		return salles.stream().filter(salle -> salle.peutAccueillir(nbParticipants))
				.max(Comparator.comparing(salle -> ((Salle) salle).getTauxOccupation(nbParticipants)));
	}
}
