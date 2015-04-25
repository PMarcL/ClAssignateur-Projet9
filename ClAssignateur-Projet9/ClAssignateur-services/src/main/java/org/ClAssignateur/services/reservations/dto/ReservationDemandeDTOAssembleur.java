package org.ClAssignateur.services.reservations.dto;

import java.util.ArrayList;
import java.util.List;

import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;

public class ReservationDemandeDTOAssembleur {

	public Demande assemblerDemande(ReservationDemandeDTO dto) {
		String titre = creerTitreDemande(dto);
		ContactsReunion groupe = creerGroupe(dto);
		Priorite priorite = new Priorite(dto.priorite);
		return new Demande(dto.nombreParticipants, groupe, titre, priorite);
	}

	private List<InformationsContact> creerListeParticipants(ReservationDemandeDTO dto) {
		List<InformationsContact> participants = new ArrayList<InformationsContact>();
		for (int indexParticipant = 0; indexParticipant < dto.participantsCourriels.size(); indexParticipant++) {
			String adresseCourriel = dto.participantsCourriels.get(indexParticipant);
			participants.add(creerEmploye(adresseCourriel));
		}
		return participants;
	}

	private InformationsContact creerEmploye(String adresseCourriel) {
		return new InformationsContact(adresseCourriel);
	}

	private ContactsReunion creerGroupe(ReservationDemandeDTO dto) {
		List<InformationsContact> participants = creerListeParticipants(dto);
		InformationsContact organisateur = creerEmploye(dto.courrielOrganisateur);
		ContactsReunion groupe = new ContactsReunion(organisateur, organisateur, participants);
		return groupe;
	}

	private String creerTitreDemande(ReservationDemandeDTO dto) {
		return "Demande pour " + dto.nombreParticipants + " personnes, par " + dto.courrielOrganisateur + ", priorité "
				+ dto.priorite;
	}

}
