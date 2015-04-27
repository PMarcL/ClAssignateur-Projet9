package org.ClAssignateur.testsAcceptationUtilisateur.fixtures;

import java.util.ArrayList;

import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;

public class ReservationDemandeDTOConstructeur {
	private final String COURRIEL_ORGANISATEUR_PAR_DEFAUT = "organisateur@domaine.com";
	private final int NOMBRE_PARTICIPANTS = 25;

	private ReservationDemandeDTO demande;

	public ReservationDemandeDTOConstructeur() {
		this.demande = new ReservationDemandeDTO();
		initialiserDemande();
	}

	private void initialiserDemande() {
		this.demande.courrielOrganisateur = COURRIEL_ORGANISATEUR_PAR_DEFAUT;
		this.demande.nombrePersonne = NOMBRE_PARTICIPANTS;
		this.demande.priorite = Priorite.moyenne().getNiveauPriorite();
		this.demande.participantsCourriels = new ArrayList<>();
	}

	public ReservationDemandeDTOConstructeur nombreParticipants(int nbParticipants) {
		this.demande.nombrePersonne = nbParticipants;
		return this;
	}

	public ReservationDemandeDTOConstructeur priorite(Priorite priorite) {
		this.demande.priorite = priorite.getNiveauPriorite();
		return this;
	}

	public ReservationDemandeDTO construireReservationDemandeDTO() {
		return this.demande;
	}
}
