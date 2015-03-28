package org.ClAssignateur.services;

import java.util.UUID;

import java.util.Optional;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class ServiceInformationDemande {

	DemandesEntrepot demandesEntrepot;

	public ServiceInformationDemande(DemandesEntrepot demandes) {
		this.demandesEntrepot = demandes;
	}

	public int getNbParticipantsSelonCourrielOrganisateur(String courrielOrganisateur, UUID idDemande) {
		Optional<Demande> demande = demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(courrielOrganisateur,
				idDemande);
		if (demande.isPresent())
			return demande.get().getNbParticipants();
		return 0;
	}

}
