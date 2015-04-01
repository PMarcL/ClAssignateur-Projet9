package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.demandes.Demande;

public class DemandeDTOAssembleur {

	private static final String AUCUNE_SALLE = "Aucune salle";

	public DemandeDTO assemblerDemandeDTO(Demande demande) {
		DemandeDTO dto = new DemandeDTO();
		dto.nombrePersonne = demande.getNbParticipants();
		dto.courrielOrganisateur = demande.getOrganisateur().courriel;
		setValeurSalleAssigne(demande, dto);
		dto.statutDemande = demande.getEtat();
		return dto;
	}

	private void setValeurSalleAssigne(Demande demande, DemandeDTO dto) {
		if (!demande.estAssignee()) {
			dto.salleAssigne = AUCUNE_SALLE;
		} else {
			dto.salleAssigne = demande.getSalleAssignee().getNom();
		}
	}

}
