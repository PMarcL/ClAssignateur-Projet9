package org.ClAssignateur.domain;

import java.util.Date;

public interface IGestionnaireDemande {

	public void SetFrequence(int frequence);

	public void SetLimite(int limite);

	public void AjouterDemande(Date debut, Date fin, int nbParticipants,
			String organisateur);
}
