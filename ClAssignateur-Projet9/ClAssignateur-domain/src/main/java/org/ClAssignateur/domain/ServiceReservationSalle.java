package org.ClAssignateur.domain;

import java.util.Calendar;

public class ServiceReservationSalle {

	private ProcessusAssignation procAssignation;

	public ServiceReservationSalle(ProcessusAssignation procAssignation) {
		this.procAssignation = procAssignation;
	}

	public void setFrequence(int frequence) {
		this.procAssignation.setFrequence(frequence);
	}

	public void setLimite(int limite) {
		this.procAssignation.setLimite(limite);
	}

	public void ajouterDemande(Calendar dateDebut, Calendar dateFin,
			int nbParticipants, String nomOrganisation) {
		this.procAssignation.ajouterDemande(dateDebut, dateFin, nbParticipants,
				nomOrganisation);
	}

	public void demarrer() {
		this.procAssignation.demarrer();
	}

	public void arreter() {
		this.procAssignation.arreter();
	}

}
