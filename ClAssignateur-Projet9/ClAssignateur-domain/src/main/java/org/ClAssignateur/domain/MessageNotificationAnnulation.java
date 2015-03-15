package org.ClAssignateur.domain;

public class MessageNotificationAnnulation implements MessageNotification {

	private Demande demande;

	public MessageNotificationAnnulation(Demande demande) {
		this.demande = demande;
	}

	public String genereMessage() {
		String message = "La demande %s a été annulée.";
		return String.format(message, demande.getTitre());
	}
}
