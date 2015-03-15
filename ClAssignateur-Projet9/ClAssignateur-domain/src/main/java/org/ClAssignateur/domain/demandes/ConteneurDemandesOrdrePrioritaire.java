package org.ClAssignateur.domain.demandes;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ConteneurDemandesOrdrePrioritaire implements ConteneurDemandes {

	private List<Demande> demandes = new ArrayList<Demande>();

	public void vider() {
		demandes.clear();
	}

	public int taille() {
		return demandes.size();
	}

	public boolean contientAuMoins(int nombreDemandes) {
		return demandes.size() >= nombreDemandes;
	}

	public void ajouterDemande(Demande demandeAjoutee) {
		demandes.add(demandeAjoutee);
	}

	public void retirerDemande(Demande demandeARetirer) {
		demandes.remove(demandeARetirer);
	}

	public boolean contientDemande(Demande demande) {
		return demandes.contains(demande);
	}

	public Iterator<Demande> iterator() {
		Comparator<Demande> parPriorite = ((demande1, demande2) -> (demande2.estPlusPrioritaire(demande1) ? 1
				: (demande1.estAussiPrioritaire(demande2) ? 0 : -1)));

		return demandes.stream().sorted(parPriorite).iterator();
	}
}
