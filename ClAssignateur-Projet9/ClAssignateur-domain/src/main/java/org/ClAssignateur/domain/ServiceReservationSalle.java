package org.ClAssignateur.domain;

public class ServiceReservationSalle {

	private DeclencheurAssignationSalle declencheurAssignationSalle;
	private FileDemande demandes;
	private EntrepotSalles salles;
	private boolean threadEnVie;

	private class TacheAssignation implements Runnable {
		public void run() {
			while (threadEnVie) {
				declencheurAssignationSalle.verifierConditionEtAssignerDemandeSalle(demandes, salles);
			}
		}
	}

	public ServiceReservationSalle(DeclencheurAssignationSalle declencheurAssignationSalle, FileDemande demandes,
			EntrepotSalles salles) {
		if (salles.estVide())
			throw new IllegalArgumentException("L'entrepôt ne contient aucune salle.");

		this.declencheurAssignationSalle = declencheurAssignationSalle;
		this.demandes = demandes;
		this.salles = salles;

		threadEnVie = false;
	}

	public void setFrequence(int frequence) {
		this.declencheurAssignationSalle.setFrequence(frequence);
	}

	public void setLimite(int limite) {
		this.declencheurAssignationSalle.setLimite(limite);
	}

	public void ajouterDemande(Demande demandeAjoutee) {
		this.demandes.ajouter(demandeAjoutee);
	}

	public void demarrer() {
		this.threadEnVie = true;
		new Thread(new TacheAssignation()).start();
	}

	public void arreter() {
		this.threadEnVie = false;
	}

}
