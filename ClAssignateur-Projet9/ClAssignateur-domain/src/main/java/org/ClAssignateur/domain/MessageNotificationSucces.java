package org.ClAssignateur.domain;

public class MessageNotificationSucces implements MessageNotification {

	private final Salle salle;

	public MessageNotificationSucces(Salle salle) {
		this.salle = salle;
	}

	@Override
	public String genereMessage() {
		String message = "La salle: %s a été réservée avec succès";
		return String.format(message, salle.getNom());
	}

	public Salle getSalle() {
		return this.salle;
	}

}
