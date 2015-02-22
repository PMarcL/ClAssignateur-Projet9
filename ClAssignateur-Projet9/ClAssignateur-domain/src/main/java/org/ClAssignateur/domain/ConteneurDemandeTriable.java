package org.ClAssignateur.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ConteneurDemandeTriable implements ConteneurDemande {

	private ArrayList<Queue<Demande>> listeFileEnOrdreCroissantdePriorite = new ArrayList<Queue<Demande>>();

	public ConteneurDemandeTriable() {
		for (int i = 1; i <= Demande.PRIORITE_MAXIMALE; i++)
			listeFileEnOrdreCroissantdePriorite.add(new LinkedList<Demande>());
	}

	private Queue<Demande> getFileSelonPriorite(int priorite) {
		return this.listeFileEnOrdreCroissantdePriorite.get(priorite - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ClAssignateur.domain.ConteneurDemande#taille()
	 */
	@Override
	public int taille() {
		int tailleTotaleInitiale = 0;

		for (Queue<Demande> file : listeFileEnOrdreCroissantdePriorite)
			tailleTotaleInitiale += file.size();

		return tailleTotaleInitiale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ClAssignateur.domain.ConteneurDemande#estVide()
	 */
	@Override
	public boolean estVide() {
		return this.taille() == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.ClAssignateur.domain.ConteneurDemande#ajouter(org.ClAssignateur.domain
	 * .Demande)
	 */
	@Override
	public void ajouter(Demande demandeAjoutee) {
		this.getFileSelonPriorite(demandeAjoutee.getPriorite()).offer(
				demandeAjoutee);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ClAssignateur.domain.ConteneurDemande#vider()
	 */
	@Override
	public void vider() {
		for (Queue<Demande> file : listeFileEnOrdreCroissantdePriorite)
			file.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.ClAssignateur.domain.ConteneurDemande#retirer()
	 */
	@Override
	public Demande retirer() throws Exception {
		for (int priorite = Demande.PRIORITE_MAXIMALE; priorite >= Demande.PRIORITE_MINIMAL; priorite--) {
			if (!this.getFileSelonPriorite(priorite).isEmpty())
				return this.getFileSelonPriorite(priorite).poll();
		}
		throw new Exception("La file est vide");
	}
}
