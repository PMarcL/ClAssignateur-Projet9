package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.demandes.Priorite;

import java.util.ArrayList;
import java.util.List;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.groupe.Groupe;
import org.ClAssignateur.domain.demandes.Demande;

public class ReservationDemandeDTOAssembleur {

	public Demande assemblerDemande(ReservationDemandeDTO dto) {
		String titre = creerTitreDemande(dto);
		List<Employe> participants = creerListeParticipants(dto);
		Employe organisateur = new Employe(dto.courrielOrganisateur);
		Groupe groupe = new Groupe(organisateur,organisateur,participants);
		Priorite priorite = new Priorite(dto.priorite);
		return new Demande(groupe, titre ,priorite);
	}

	private List<Employe> creerListeParticipants(ReservationDemandeDTO dto) {
		List<Employe> participants = new ArrayList<Employe>();
		for (String courrielParticipant : dto.courrielParticipants){
			Employe participant = new Employe(courrielParticipant);
			participants.add(participant);
		}
		return participants;
	}

	private String creerTitreDemande(ReservationDemandeDTO dto) {
		return "Demande pour " + dto.nombrePersonnes + " personnes, par " + dto.courrielOrganisateur + ", priorité "
				+ dto.priorite;
	}

}
