package org.ClAssignateur.domain;

public class MessageNotificationEchec implements MessageNotification {

	@Override
	public String genereMessage() {
		String message = "La demande n'a pas pu être assigné: aucune salle ne correspond à la demande";
		return message;
	}

}
