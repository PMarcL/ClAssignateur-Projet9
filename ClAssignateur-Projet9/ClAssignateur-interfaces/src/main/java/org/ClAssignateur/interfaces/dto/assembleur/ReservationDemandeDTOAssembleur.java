package org.ClAssignateur.interfaces.dto.assembleur;

import java.util.ArrayList;
import java.util.List;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.groupe.Employe;
import org.ClAssignateur.domaine.groupe.Groupe;
import org.ClAssignateur.interfaces.dto.ReservationDemandeDTO;

public class ReservationDemandeDTOAssembleur {

	public Demande assemblerDemande(ReservationDemandeDTO dto) {
		String titre = creerTitreDemande(dto);
		Groupe groupe = creerGroupe(dto);
		Priorite priorite = new Priorite(dto.priorite);
		return new Demande(groupe, titre, priorite);
	}

	private List<Employe> creerListeParticipants(ReservationDemandeDTO dto) {
		List<Employe> participants = new ArrayList<Employe>();
		for (int indexParticipant = 0; indexParticipant < dto.nombrePersonnes; indexParticipant++) {
			String adresseCourriel = dto.participantsCourriels.get(indexParticipant);
			participants.add(creerEmploye(adresseCourriel));
		}
		return participants;
	}

	private Employe creerEmploye(String adresseCourriel) {
		return new Employe(adresseCourriel);
	}

	private Groupe creerGroupe(ReservationDemandeDTO dto) {
		List<Employe> participants = creerListeParticipants(dto);
		Employe organisateur = creerEmploye(dto.courrielOrganisateur);
		Groupe groupe = new Groupe(organisateur, organisateur, participants);
		return groupe;
	}

	private String creerTitreDemande(ReservationDemandeDTO dto) {
		return "Demande pour " + dto.nombrePersonnes + " personnes, par " + dto.courrielOrganisateur + ", priorité "
				+ dto.priorite;
	}

}
