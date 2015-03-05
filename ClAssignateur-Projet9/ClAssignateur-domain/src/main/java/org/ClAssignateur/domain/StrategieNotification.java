package org.ClAssignateur.domain;

public interface StrategieNotification {

	public void notifierEchecAssignation(Employe employe);

	public void notifierAssignation(Salle salleAssigne, Employe employe);

}
