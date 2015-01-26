package org.ClAssignateur.domain;

import java.util.Date;

public interface IGestionnaireDemande {

	public void setFrequence(int frequence);

	public void setLimite(int limite);

	public void ajouterDemande(Date debut, Date fin, int nbParticipants,
			String organisateur);
}
