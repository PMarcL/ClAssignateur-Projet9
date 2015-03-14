package org.ClAssignateur.domain;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class SelectionSalleOptimaleStrategie implements SelectionSalleStrategie {

	@Override
	public Optional<Salle> selectionnerSalle(Collection<Salle> salles, Demande demande) {
		int nbParticipants = demande.getNbParticipant();
		return salles.stream().filter(salle -> salle.peutAccueillir(nbParticipants))
				.max(Comparator.comparing(salle -> ((Salle) salle).getPourcentageOccupation(nbParticipants)));
	}
}
