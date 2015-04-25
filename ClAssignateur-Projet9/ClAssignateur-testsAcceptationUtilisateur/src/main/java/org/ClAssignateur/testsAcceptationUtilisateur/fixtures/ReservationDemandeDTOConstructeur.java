package org.ClAssignateur.testsAcceptationUtilisateur.fixtures;

import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;

public class ReservationDemandeDTOConstructeur {
	private ReservationDemandeDTO demande;

	public ReservationDemandeDTOConstructeur nombreParticipants(int nbParticipants) {
		demande.nombreParticipants = nbParticipants;
		return this;
	}

	public ReservationDemandeDTO construireReservationDemandeDTO() {
		return demande;
	}
}
