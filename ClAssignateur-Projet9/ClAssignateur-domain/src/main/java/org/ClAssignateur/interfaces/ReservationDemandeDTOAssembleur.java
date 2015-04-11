package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.groupe.AdresseCourriel;

import org.ClAssignateur.domain.demandes.Priorite;
import java.util.ArrayList;
import java.util.List;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.groupe.Groupe;
import org.ClAssignateur.domain.demandes.Demande;

public class ReservationDemandeDTOAssembleur {

	public Demande assemblerDemande(ReservationDemandeDTO dto) throws ReservationDemandeDTOInvalideException {
		validerConcordanceNbPersonnesVsNbCourriels(dto.nombrePersonnes, dto.participantsCourriels);

		String titre = creerTitreDemande(dto);
		Groupe groupe = creerGroupe(dto);
		Priorite priorite = new Priorite(dto.priorite);
		return new Demande(groupe, titre, priorite);
	}

	private void validerConcordanceNbPersonnesVsNbCourriels(int nbPersonnes, List<String> courrielsParticipants) {
		if (nbPersonnes != courrielsParticipants.size())
			throw new ReservationDemandeDTOInvalideException(
					"Le nombre de personnes ne correspond pas au nombre d'adresseses courriel reçues.");
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
		return new Employe(new AdresseCourriel(adresseCourriel));
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
