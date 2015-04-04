package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.groupe.Employe;

import org.ClAssignateur.domain.groupe.Groupe;
import org.ClAssignateur.domain.demandes.Demande;

public class ReservationDemandeDTOAssembleur {

	public Demande assemblerDemande(ReservationDemandeDTO dto) {
		Employe organisateur = new Employe(dto.courrielOrganisateur);
		Groupe groupe = new Groupe(organisateur, dto.nombrePersonnes);
		String titre = creerTitreDemande(dto);

		return new Demande(groupe, titre);
	}

	private String creerTitreDemande(ReservationDemandeDTO dto) {
		return "Demande pour " + dto.nombrePersonnes + " personnes, par " + dto.courrielOrganisateur + ", priorité "
				+ dto.priorite;
	}

}
