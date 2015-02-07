package org.ClAssignateur.domain;

import java.util.Calendar;

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

	public ServiceReservationSalle(DeclencheurAssignationSalle declencheurAssignationSalle,
			FileDemande demandes, EntrepotSalles salles) {
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

	public void ajouterDemande(Calendar dateDebut, Calendar dateFin,
			int nbParticipants, String nomOrganisation) {
		this.demandes.ajouter(dateDebut, dateFin, nbParticipants,
				nomOrganisation);
	}

	public void demarrer() {
		this.threadEnVie = true;
		new Thread(new TacheAssignation()).start();
	}

	public void arreter() {
		this.threadEnVie = false;
	}

}
