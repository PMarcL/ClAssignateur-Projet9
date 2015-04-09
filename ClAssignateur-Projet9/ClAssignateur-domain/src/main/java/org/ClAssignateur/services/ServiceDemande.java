package org.ClAssignateur.services;

import java.util.List;

import java.util.UUID;
import java.util.Optional;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class ServiceDemande {

	DemandesEntrepot demandesEntrepot;

	public ServiceDemande(DemandesEntrepot demandes) {
		this.demandesEntrepot = demandes;
	}

	public Demande getInfoDemandePourCourrielEtId(String courrielOrganisateur, UUID idDemande) {
		Optional<Demande> demande = demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(courrielOrganisateur,
				idDemande);
		if (!demande.isPresent()) {
			throw new DemandePasPresenteException(
					"Aucune demande ne correspond au courriel d'organisateur ou au numéro donné.");
		}
		return demande.get();
	}

	public List<Demande> getDemandesPourCourriel(String courrielOrganisateur) {
		return demandesEntrepot.obtenirDemandesSelonCourriel(courrielOrganisateur);
	}

	public void ajouterDemande(Demande demande){
		demandesEntrepot.persisterDemande(demande);
	}
}
