package org.ClAssignateur.domain;

public class MessageNotificationSuccess implements MessageNotification {

	private final Salle salle;

	public MessageNotificationSuccess(Salle salle) {
		this.salle = salle;
	}

	@Override
	public String genereMessage() {
		String message = "La salle: %s a été réservée avec succès";
		return String.format(message, salle.toString());
	}

}
