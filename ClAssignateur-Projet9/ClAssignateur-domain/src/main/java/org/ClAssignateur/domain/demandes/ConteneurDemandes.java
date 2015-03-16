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

	public List<Demande> obtenirDemandesEnAttente() {
		return trierDemandesParPriorite(this.demandesEnAttente.obtenirDemandes());
	}

	private List<Demande> trierDemandesParPriorite(List<Demande> demandesATrier) {
		Comparator<Demande> parPriorite = ((demande1, demande2) -> (demande2.estPlusPrioritaire(demande1) ? 1
				: (demande1.estAussiPrioritaire(demande2) ? 0 : -1)));

		demandesATrier.sort(parPriorite);
		return demandesATrier;
	}

	public Optional<Demande> trouverDemandeSelonTitreReunion(String titreReunion) {
		Optional<Demande> demandeObtenue = this.demandesArchivees.obtenirDemandeSelonTitre(titreReunion);
		if (!demandeObtenue.isPresent()) {
			demandeObtenue = this.demandesEnAttente.obtenirDemandeSelonTitre(titreReunion);
		}

		return demandeObtenue;
	}

	public void archiverDemande(Demande demandeFaiblePriorite) {
		this.demandesArchivees.persisterDemande(demandeFaiblePriorite);
	}

	public boolean contientAuMoinsEnAttente(int nbDemandesEnAttente) {
		return (this.demandesEnAttente.taille() >= nbDemandesEnAttente);
	}

	// public void vider() {
	// demandes.clear();
	// }
	//
	// public int taille() {
	// return demandes.size();
	// }
	//
	// public boolean contientAuMoins(int nombreDemandes) {
	// return demandes.size() >= nombreDemandes;
	// }
	//
	// public void ajouterDemande(Demande demandeAjoutee) {
	// demandes.add(demandeAjoutee);
	// }
	//
	// public void retirerDemande(Demande demandeARetirer) {
	// demandes.remove(demandeARetirer);
	// }
	//
	// public boolean contientDemande(Demande demande) {
	// return demandes.contains(demande);
	// }
	//
	// public Iterator<Demande> iterator() {
	// Comparator<Demande> parPriorite = ((demande1, demande2) ->
	// (demande2.estPlusPrioritaire(demande1) ? 1
	// : (demande1.estAussiPrioritaire(demande2) ? 0 : -1)));
	//
	// return demandes.stream().sorted(parPriorite).iterator();
	// }
}
