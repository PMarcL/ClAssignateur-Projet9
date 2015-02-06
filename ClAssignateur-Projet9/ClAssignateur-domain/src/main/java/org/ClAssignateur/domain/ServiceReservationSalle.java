package org.ClAssignateur.domain;

import java.util.concurrent.Executor;
import java.util.Calendar;

public class ServiceReservationSalle implements Runnable {

	private AssignateurSalle assignateurSalle;
	private FileDemande demandes;
	private EntrepotSalles salles;
	private Executor executeur;
	private boolean serviceEnFonction;

	public ServiceReservationSalle(AssignateurSalle assignateurSalle, FileDemande demandes, EntrepotSalles salles,
			Executor executeur) {
		this.assignateurSalle = assignateurSalle;
		this.demandes = demandes;
		this.salles = salles;
		serviceEnFonction = true;
		this.executeur = executeur;
		this.executeur.execute(this);
	}

	public void setFrequence(int frequence) {
		this.assignateurSalle.setFrequence(frequence);
	}

	public void setLimite(int limite) {
		this.assignateurSalle.setLimite(limite);
	}

	public void ajouterDemande(Calendar dateDebut, Calendar dateFin, int nbParticipants, String nomOrganisation) {
		this.demandes.ajouter(dateDebut, dateFin, nbParticipants, nomOrganisation);
	}

	public void ajouterDemande(Demande demande) {
		this.demandes.ajouter(demande);
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
			assignateurSalle.assignerDemandeSalle(demandes, salles);
		}
	}

}
