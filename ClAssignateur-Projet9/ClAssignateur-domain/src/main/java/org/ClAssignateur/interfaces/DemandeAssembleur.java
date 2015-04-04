package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.groupe.Employe;

import org.ClAssignateur.domain.groupe.Groupe;
import org.ClAssignateur.domain.demandes.Demande;

public class DemandeAssembleur {

	private static final String AUCUNE_SALLE = "Aucune salle";

	public Demande assemblerDemande(DemandeDTO dto) {
		Employe organisateur = new Employe(dto.courrielOrganisateur);
		Groupe groupe = new Groupe(organisateur,dto.nombrePersonne);
		
		String titre = "Demande " + dto.nombrePersonne + " personnes, par " + 
				dto.courrielOrganisateur + ", priorité " + dto.priorite;
		
		Demande demande = new Demande(groupe,titre);
		return demande;
	}

}
