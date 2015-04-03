package org.ClAssignateur.services;

import org.ClAssignateur.interfaces.DemandeDTOAssembleur;

import org.ClAssignateur.interfaces.DemandeDTO;
import java.util.UUID;
import java.util.Optional;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class ServiceDemande {

	DemandesEntrepot demandesEntrepot;
	private DemandeDTOAssembleur demandeAssembleur;

	public ServiceDemande(DemandesEntrepot demandes, DemandeDTOAssembleur demandeAssembleur) {
		this.demandesEntrepot = demandes;
		this.demandeAssembleur = demandeAssembleur;
	}

	public DemandeDTO getInfoDemandePourCourrielEtId(String courrielOrganisateur, UUID idDemande) {
		Optional<Demande> demande = demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(courrielOrganisateur,
				idDemande);
		if (!demande.isPresent()) {
			throw new DemandePasPresenteException(
					"Aucune demande ne correspond au courriel d'organisateur ou au numéro donné.");
		}
		return demandeAssembleur.assemblerDemandeDTO(demande.get());
	}

}
