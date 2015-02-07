package org.ClAssignateur.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class FileDemande {

	private ArrayList<Queue<Demande>> listeFileEnOrdreCroissantdePriorite = new ArrayList<Queue<Demande>>();

	public FileDemande() {
		for (int i = 1; i <= Demande.PRIORITE_MAXIMALE; i++)
			listeFileEnOrdreCroissantdePriorite.add(new LinkedList<Demande>());
	}

	private Queue<Demande> getFileSelonPriorite(int priorite) {
		return this.listeFileEnOrdreCroissantdePriorite.get(priorite - 1);
	}

	public int taille() {
		int tailleTotaleInitiale = 0;

		for (Queue<Demande> file : listeFileEnOrdreCroissantdePriorite)
			tailleTotaleInitiale += file.size();

		return tailleTotaleInitiale;
	}

	public boolean estVide() {
		return this.taille()== 0;
	}

	public void ajouter(Calendar dateDebut, Calendar dateFin,
			int nombreParticipant, String organisateur) {
		
		Demande demandeAjoutee = new Demande(dateDebut, dateFin,
				nombreParticipant, organisateur);

		this.getFileSelonPriorite(demandeAjoutee.getPriorite()).offer(
				demandeAjoutee);
	}

	public void ajouter(Demande demandeAjoutee) {
		this.getFileSelonPriorite(demandeAjoutee.getPriorite()).offer(
				demandeAjoutee);
	}

	public void vider() {
		for (Queue<Demande> file : listeFileEnOrdreCroissantdePriorite)
			file.clear();
	}

	public Demande retirer() throws Exception {
		for (int priorite = Demande.PRIORITE_MAXIMALE; priorite >= Demande.PRIORITE_MINIMAL; priorite--) {
			if (!this.getFileSelonPriorite(priorite).isEmpty())
				return this.getFileSelonPriorite(priorite).poll();
		}
		throw new Exception("La file est vide");
	}
}
