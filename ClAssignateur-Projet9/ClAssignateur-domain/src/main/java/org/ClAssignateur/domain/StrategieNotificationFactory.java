package org.ClAssignateur.domain;

public class StrategieNotificationFactory {

	public StrategieNotification creerStrategieNotification() {
		StrategieNotification strategie = new StrategieNotificationCourriel();
		return strategie;
	}
}
