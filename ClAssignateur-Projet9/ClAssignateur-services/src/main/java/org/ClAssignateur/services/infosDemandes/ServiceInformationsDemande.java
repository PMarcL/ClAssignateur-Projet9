package org.ClAssignateur.services.infosDemandes;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTOAssembleur;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTO;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTOAssembleur;
import org.ClAssignateur.services.localisateur.LocalisateurServices;

public class ServiceInformationsDemande {

	ConteneurDemandes conteneurDemandes;
	InformationsDemandeDTOAssembleur infosDemandeAssembleur;
	OrganisateurDemandesDTOAssembleur organisateurDemandesAssembleur;

	public ServiceInformationsDemande() {
		this.conteneurDemandes = LocalisateurServices.getInstance().obtenir(ConteneurDemandes.class);
		this.infosDemandeAssembleur = new InformationsDemandeDTOAssembleur();
		this.organisateurDemandesAssembleur = new OrganisateurDemandesDTOAssembleur();
	}

	public ServiceInformationsDemande(ConteneurDemandes demandes,
			InformationsDemandeDTOAssembleur infosDemandeAssembleur,
			OrganisateurDemandesDTOAssembleur organisateurDemandesAssembleur) {
		this.conteneurDemandes = demandes;
		this.infosDemandeAssembleur = infosDemandeAssembleur;
		this.organisateurDemandesAssembleur = organisateurDemandesAssembleur;
	}

	public InformationsDemandeDTO getInformationsDemande(String courrielOrganisateur, UUID idDemande) {
		Optional<Demande> demande = conteneurDemandes.obtenirDemandeSelonId(idDemande);
		if (demandeAppartienOrganisateur(courrielOrganisateur, demande)) {
			return this.infosDemandeAssembleur.assemblerInformationsDemandeDTO(demande.get());
		}

		throw new DemandeIntrouvableException(
				"Aucune demande ne correspond au courriel d'organisateur ou au numéro donné.");
	}

	private boolean demandeAppartienOrganisateur(String courrielOrganisateur, Optional<Demande> demande) {
		if (demande.isPresent()) {
			return demande.get().getCourrielOrganisateur().equals(courrielOrganisateur);
		} else {
			return false;
		}
	}

	public OrganisateurDemandesDTO getDemandesOrganisateur(String courrielOrganisateur) {
		List<Demande> demandes = conteneurDemandes.obtenirDemandesSelonCourrielOrganisateur(courrielOrganisateur);
		return this.organisateurDemandesAssembleur.assemblerOrganisateurDemandesDTO(demandes);
	}
}
