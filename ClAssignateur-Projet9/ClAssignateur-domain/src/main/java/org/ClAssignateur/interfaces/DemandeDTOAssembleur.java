package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.demandes.Demande;

public class DemandeDTOAssembleur {

	public DemandeDTO assemblerDemandeDTO(Demande demande) {
		DemandeDTO dto = new DemandeDTO();
		dto.nbParticipants = demande.getNbParticipants();
		dto.organisateur = demande.getOrganisateur();
		dto.responsable = demande.getResponsable();
		dto.identifiant = demande.getID();
		dto.salleAssignee = demande.getSalleAssignee();
		return dto;
	}

}
