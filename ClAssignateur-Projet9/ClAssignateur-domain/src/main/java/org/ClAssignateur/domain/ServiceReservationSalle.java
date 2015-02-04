package org.ClAssignateur.domain;

import java.util.Calendar;

public class ServiceReservationSalle {

	private AssignateurSalle assignateurSalle;
	private FileDemande demandes;
	private EntrepotSalles salles;

	public ServiceReservationSalle(AssignateurSalle assignateurSalle,
			FileDemande demandes, EntrepotSalles salles) {
		this.assignateurSalle = assignateurSalle;
		this.demandes = demandes;
		this.salles = salles;
	}

	public void setFrequence(int frequence) {
		this.assignateurSalle.setFrequence(frequence);
	}

	public void setLimite(int limite) {
		this.assignateurSalle.setLimite(limite);
	}

	public void ajouterDemande(Calendar dateDebut, Calendar dateFin,
			int nbParticipants, String nomOrganisation) {
		this.demandes.ajouter(dateDebut, dateFin, nbParticipants,
				nomOrganisation);
	}

}
