package org.ClAssignateur.domain;

import java.util.Calendar;

public class ServiceReservationSalle {

	private GestionnaireDemande gestionnaireDemande;

	public ServiceReservationSalle(GestionnaireDemande gestDemande) {
		this.gestionnaireDemande = gestDemande;
	}

	public void setFrequence(int frequence) {
		this.gestionnaireDemande.setFrequence(frequence);
	}

	public void setLimite(int limite) {
		this.gestionnaireDemande.setLimite(limite);
	}

	public void ajouterDemande(Calendar dateDebut, Calendar dateFin,
			int nbParticipants, String nomOrganisation) {
		this.gestionnaireDemande.ajouterDemande(dateDebut, dateFin,
				nbParticipants, nomOrganisation);
	}

}
