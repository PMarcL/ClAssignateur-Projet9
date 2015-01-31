package org.ClAssignateur.domain;

import java.util.Calendar;

public class ProcessusAssignation {

	private AssignateurSalle assignateurSalle;
	private boolean peutContinuer;

	public ProcessusAssignation(AssignateurSalle assignateurSalle) {
		this.assignateurSalle = assignateurSalle;
		this.peutContinuer = false;
	}

	public void demarrer() {
		this.peutContinuer = true;
		(new Thread(new ProcessusConcurent())).start();
	}

	public void arreter() {
		this.peutContinuer = false;
	}

	public void setFrequence(int frequence) {
		this.assignateurSalle.setFrequence(frequence);
	}

	public void setLimite(int limite) {
		this.assignateurSalle.setLimite(limite);
	}

	public void ajouterDemande(Calendar dateDebut, Calendar dateFin,
			int nombreParticipant, String nomOrganisateur) {
		this.assignateurSalle.ajouterDemande(dateDebut, dateFin,
				nombreParticipant, nomOrganisateur);
	}

	private class ProcessusConcurent implements Runnable {
		public void run() {
			while (peutContinuer) {
				assignateurSalle.verifierConditionAssignation();
			}
		}
	}

}
