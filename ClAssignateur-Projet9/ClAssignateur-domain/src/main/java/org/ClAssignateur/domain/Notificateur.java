package org.ClAssignateur.domain;

public interface Notificateur {

	public void notifierSucces(Demande demande, Salle salleAssignee);

	public void notifierEchec(Demande demande);

	public void notifierAnnulation(Demande demande);
}
