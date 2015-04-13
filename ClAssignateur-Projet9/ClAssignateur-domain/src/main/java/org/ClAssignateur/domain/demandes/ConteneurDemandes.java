package org.ClAssignateur.domain.demandes;

import java.util.Optional;
import java.util.Comparator;
import java.util.List;

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

	public List<Demande> obtenirDemandesEnAttenteEnOrdreDePriorite() {
		List<Demande> demandesTriees = trierDemandesParPriorite(this.demandesEnAttente.obtenirDemandes());
		demandesEnAttente.vider();
		return demandesTriees;
	}

	private List<Demande> trierDemandesParPriorite(List<Demande> demandesATrier) {
		Comparator<Demande> parPriorite = ((demande1, demande2) -> (demande2.estPlusPrioritaire(demande1) ? 1
				: (demande1.estAussiPrioritaire(demande2) ? (demande1.estAnterieureA(demande2) ? -1 : 1) : -1)));

		demandesATrier.sort(parPriorite);
		return demandesATrier;
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
}
