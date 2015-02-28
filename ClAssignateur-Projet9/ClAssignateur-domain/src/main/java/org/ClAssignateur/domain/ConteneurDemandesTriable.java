package org.ClAssignateur.domain;

import java.util.Iterator;

import java.util.PriorityQueue;

public class ConteneurDemandesTriable implements ConteneurDemandes {

	private PriorityQueue<Demande> file = new PriorityQueue<Demande>(new ComparateurDeDemande());

	@Override
	public boolean contientAuMoins(int nombreDemandes) {
		// return file.size() >= nombreDemandes;

		return true;
	}

	// @Override
	// public boolean estVide() {
	// return file.isEmpty();
	// }

	@Override
	public void ajouterDemande(Demande demandeAjoutee) {
		file.add(demandeAjoutee);
	}

	@Override
	public Iterator<Demande> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void vider() {
		// TODO Auto-generated method stub

	}

	// @Override
	// public void vider() {
	// file.clear();
	// }

	// @Override
	// public Demande retirer() {
	// return file.remove();
	// }
}
