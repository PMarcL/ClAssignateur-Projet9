package org.ClAssignateur.domain;

import java.util.PriorityQueue;

public class ConteneurDemandesTriable implements ConteneurDemandes {

	private PriorityQueue<Demande> file = new PriorityQueue<Demande>(
			new ComparateurDeDemande());

	@Override
	public int taille() {
		return file.size();
	}

	@Override
	public boolean estVide() {
		return file.isEmpty();
	}

	@Override
	public void ajouter(Demande demandeAjoutee) {
		file.add(demandeAjoutee);
	}

	@Override
	public void vider() {
		file.clear();
	}

	@Override
	public Demande retirer() {
		return file.remove();
	}
}
