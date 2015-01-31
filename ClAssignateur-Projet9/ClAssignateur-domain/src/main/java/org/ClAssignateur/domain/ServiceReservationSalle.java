package org.ClAssignateur.domain;

import java.util.Date;

import ca.ulaval.glo4002.ProjetMargot.GestionnaireDemande;

public class ServiceReservationSalle {
	public void setFrequence(GestionnaireDemande monGestionnaireDemande,int frequence) {
		monGestionnaireDemande.setFrequence(frequence);
	}

	public void setLimite(GestionnaireDemande monGestionnaireDemande, int limite) {
		monGestionnaireDemande.setLimite(limite);
	}

	public void ajouterDemande(GestionnaireDemande monGestionnaireDemande,
	    Date dateDebut, Date dateFin, int nbParticipants,
	    String nomOrganisation) {
		monGestionnaireDemande.ajouterDemande(dateDebut, dateFin, nbParticipants, nomOrganisation);
	}

}
