package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.demandes.Demande;

public class DemandeDTOAssembleur {

	public DemandeDTO assemblerDemandeDTO(Demande demande) {
		DemandeDTO dto = new DemandeDTO();
		dto.nombrePersonne = demande.getNbParticipants();
		dto.courrielOrganisateur = demande.getOrganisateur().courriel;
		dto.salleDemandee = demande.getSalleAssignee().getNom();
		dto.salleAssignee = demande.getSalleAssignee().getNom();
		dto.etatDemande = demande.getEtat();
		return dto;
	}

}
