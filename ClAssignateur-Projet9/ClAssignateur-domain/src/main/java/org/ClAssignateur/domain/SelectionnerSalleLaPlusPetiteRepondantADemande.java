package org.ClAssignateur.domain;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class SelectionnerSalleLaPlusPetiteRepondantADemande implements StrategieDeSelectionDeSalle {

	@Override
	public Optional<Salle> appliquer(Collection<Salle> salles, Demande demande) {
		int nbParticipants = demande.getNbParticipant();
		return salles.stream().filter(salle -> salle.peutAccueillir(nbParticipants))
				.max(Comparator.comparing(salle -> ((Salle) salle).getPourcentageOccupation(nbParticipants)));
	}
}
