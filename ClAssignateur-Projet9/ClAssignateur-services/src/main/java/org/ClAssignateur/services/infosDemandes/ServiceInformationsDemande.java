package org.ClAssignateur.services.infosDemandes;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTOAssembleur;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTO;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTOAssembleur;

public class ServiceInformationsDemande {

	DemandesEntrepot demandesEntrepot;
	InformationsDemandeDTOAssembleur infosDemandeAssembleur;
	OrganisateurDemandesDTOAssembleur organisateurDemandesAssembleur;

	public ServiceInformationsDemande(DemandesEntrepot demandes,
			InformationsDemandeDTOAssembleur infosDemandeAssembleur,
			OrganisateurDemandesDTOAssembleur organisateurDemandesAssembleur) {
		this.demandesEntrepot = demandes;
		this.infosDemandeAssembleur = infosDemandeAssembleur;
		this.organisateurDemandesAssembleur = organisateurDemandesAssembleur;
	}

	public InformationsDemandeDTO getInformationsDemande(String courrielOrganisateur, UUID idDemande) {
		Optional<Demande> demande = demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(courrielOrganisateur,
				idDemande);
		if (!demande.isPresent()) {
			throw new DemandeIntrouvableException(
					"Aucune demande ne correspond au courriel d'organisateur ou au numéro donné.");
		}

		return this.infosDemandeAssembleur.assemblerInformationsDemandeDTO(demande.get());
	}

	public OrganisateurDemandesDTO getDemandesOrganisateur(String courrielOrganisateur) {
		List<Demande> demandes = demandesEntrepot.obtenirDemandesSelonCourriel(courrielOrganisateur);
		return this.organisateurDemandesAssembleur.assemblerOrganisateurDemandesDTO(demandes);
	}
}
