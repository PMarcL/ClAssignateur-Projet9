package org.ClAssignateur.services;

import org.ClAssignateur.interfaces.DemandeResultatAssembleur;

import org.ClAssignateur.interfaces.DemandeResultat;
import java.util.UUID;
import java.util.Optional;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class ServiceDemande {

	DemandesEntrepot demandesEntrepot;
	private DemandeResultatAssembleur demandeAssembleur;

	public ServiceDemande(DemandesEntrepot demandes, DemandeResultatAssembleur demandeAssembleur) {
		this.demandesEntrepot = demandes;
		this.demandeAssembleur = demandeAssembleur;
	}

	public DemandeResultat getInfoDemandePourCourrielEtId(String courrielOrganisateur, UUID idDemande) {
		Optional<Demande> demande = demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(courrielOrganisateur,
				idDemande);
		if (demande.isPresent()) {
			demandeAssembleur.assemblerDemande(demande.get());
		} else {
			throw new DemandePasPresenteException(
					"Aucune demande ne correspond au courriel d'organisateur ou au numéro donné.");
		}

		return new DemandeResultat(1);
	}

}
