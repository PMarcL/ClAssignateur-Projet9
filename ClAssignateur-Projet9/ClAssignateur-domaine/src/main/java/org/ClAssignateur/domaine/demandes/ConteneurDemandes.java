package org.ClAssignateur.domaine.demandes;

import java.util.UUID;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ConteneurDemandes {

	private DemandesEntrepot demandesEnAttente;
	private DemandesEntrepot demandesArchivees;

	public ConteneurDemandes(DemandesEntrepot demandesEnAttente, DemandesEntrepot demandesArchivees) {
		this.demandesEnAttente = demandesEnAttente;
		this.demandesArchivees = demandesArchivees;
	}

	public void mettreDemandeEnAttente(Demande demande) {
		this.demandesEnAttente.persisterDemande(demande);
	}

	public List<Demande> obtenirDemandesEnAttenteOrdrePrioritaire() {
		List<Demande> demandesTriees = trierDemandesParPriorite(this.demandesEnAttente.obtenirDemandes());
		demandesEnAttente.vider();
		return demandesTriees;
	}

	private List<Demande> trierDemandesParPriorite(List<Demande> demandesATrier) {
		Comparator<Demande> parPriorite = ((demande1, demande2) -> ordonnerDeuxDemandes(demande1, demande2));

		demandesATrier.sort(parPriorite);
		return demandesATrier;
	}

	private int ordonnerDeuxDemandes(Demande demande1, Demande demande2) {
		if (demande1.estPlusPrioritaire(demande2)) {
			return -1;
		} else if (demande2.estAussiPrioritaire(demande1)) {
			return 1;
		} else {
			return comparerOrdreCreationDemande(demande1, demande2);
		}
	}

	private int comparerOrdreCreationDemande(Demande demande1, Demande demande2) {
		if (demande1.estAnterieureA(demande2)) {
			return -1;
		} else {
			return 1;
		}
	}

	public Optional<Demande> trouverDemandeSelonTitreReunion(String titreReunion) {
		Optional<Demande> demandeObtenue = this.demandesArchivees.obtenirDemandeSelonTitre(titreReunion);

		if (demandeObtenue.isPresent()) {
			this.demandesArchivees.retirerDemande(demandeObtenue.get());
		} else {
			demandeObtenue = obtenirDansDemandeEnAttente(titreReunion);
		}

		return demandeObtenue;
	}

	private Optional<Demande> obtenirDansDemandeEnAttente(String titreReunion) {
		Optional<Demande> demandeObtenue = this.demandesEnAttente.obtenirDemandeSelonTitre(titreReunion);
		if (demandeObtenue.isPresent()) {
			this.demandesEnAttente.retirerDemande(demandeObtenue.get());
		}
		return demandeObtenue;
	}

	public void archiverDemande(Demande demande) {
		this.demandesArchivees.persisterDemande(demande);
	}

	public int getNombreDemandesEnAttente() {
		return this.demandesEnAttente.taille();
	}

	public List<Demande> obtenirDemandesSelonCourrielOrganisateur(String courrielOrganisateur) {
		List<Demande> demandesOrganisateur = obtenirDemandesEnAttenteOrganisateur(courrielOrganisateur);
		demandesOrganisateur.addAll(obtenirDemandesArchiveesOrganisateur(courrielOrganisateur));
		return demandesOrganisateur;
	}

	private List<Demande> obtenirDemandesEnAttenteOrganisateur(String courrielOrganisateur) {
		return demandesEnAttente.obtenirDemandesSelonCourrielOrganisateur(courrielOrganisateur);
	}

	private List<Demande> obtenirDemandesArchiveesOrganisateur(String courrielOrganisateur) {
		return demandesArchivees.obtenirDemandesSelonCourrielOrganisateur(courrielOrganisateur);
	}

	public Optional<Demande> obtenirDemandeSelonId(UUID idDemande) {
		Optional<Demande> demandeObtenue = demandesEnAttente.obtenirDemandeSelonId(idDemande);
		if (!demandeObtenue.isPresent())
			demandeObtenue = demandesArchivees.obtenirDemandeSelonId(idDemande);

		return demandeObtenue;
	}
}
