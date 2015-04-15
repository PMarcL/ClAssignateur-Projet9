package org.ClAssignateur.interfaces.dto.assembleur;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.interfaces.dto.InformationsDemandeDTO;

public class InformationsDemandeDTOAssembleur {

	private final String AUCUNE_SALLE = null;

	public InformationsDemandeDTO assemblerInformationsDemandeDTO(Demande demande) {
		InformationsDemandeDTO dto = new InformationsDemandeDTO();
		dto.nombrePersonne = demande.getNbParticipants();
		dto.courrielOrganisateur = demande.getOrganisateur().courriel.toString();
		setValeurSalleAssigne(demande, dto);
		dto.statutDemande = demande.getEtat();
		return dto;
	}

	private void setValeurSalleAssigne(Demande demande, InformationsDemandeDTO dto) {
		if (!demande.estAssignee()) {
			dto.salleAssigne = AUCUNE_SALLE;
		} else {
			dto.salleAssigne = demande.getSalleAssignee().getNom();
		}
	}

}
