package org.ClAssignateur.domain;

import java.util.Comparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class ConteneurDemandesOrdrePrioritaire implements ConteneurDemandes {

	private List<Demande> demandes = new ArrayList<Demande>();

	@Override
	public boolean contientAuMoins(int nombreDemandes) {
		return demandes.size() >= nombreDemandes;
	}

	@Override
	public void ajouterDemande(Demande demandeAjoutee) {
		demandes.add(demandeAjoutee);
	}

	@Override
	public Iterator<Demande> iterator() {
		Comparator<Demande> parPriorite = ((demande1, demande2) -> (demande2.estPlusPrioritaire(demande1) ? 1
				: (demande1.estAutantPrioritaire(demande2) ? 0 : -1)));

		return demandes.stream().sorted(parPriorite).iterator();
	}

	@Override
	public void vider() {
		demandes.clear();
	}
}
