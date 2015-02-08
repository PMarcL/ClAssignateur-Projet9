package org.ClAssignateur.domain;

import java.util.concurrent.Executor;

public class ServiceReservationSalle implements Runnable {

	private DeclencheurAssignationSalle declencheurAssignationSalle;
	private FileDemande demandes;
	private EntrepotSalles salles;
	private Executor executeur;
	private boolean serviceEnFonction;

	public ServiceReservationSalle(DeclencheurAssignationSalle declencheurAssignationSalle, FileDemande demandes,
			EntrepotSalles salles, Executor executeur) {
		if (salles.estVide())
			throw new IllegalArgumentException("L'entrepôt ne contient aucune salle.");

		this.declencheurAssignationSalle = declencheurAssignationSalle;
		this.demandes = demandes;
		this.salles = salles;
		serviceEnFonction = true;
		this.executeur = executeur;
		this.executeur.execute(this);
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

	public void arreterService() {
		serviceEnFonction = false;
	}

	public void run() {
		while (serviceEnFonction) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				arreterService();
				System.out.println("Erreur d'exécution, le système doit s'arrêter. Message d'erreur:\n"
						+ e.getMessage());
			}
			declencheurAssignationSalle.verifierConditionEtAssignerDemandeSalle(demandes, salles);
		}
	}

}
