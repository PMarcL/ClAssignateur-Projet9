package org.ClAssignateur.domain;

public class MessageNotificationFactory {

	public MessageNotification genereNotificationSucces(Salle salleAssigne) {
		MessageNotification messageSucces = new MessageNotificationSucces(salleAssigne);
		return messageSucces;
	}

	public MessageNotification genereNotificationEchec() {
		MessageNotification messageSucces = new MessageNotificationEchec();
		return messageSucces;
	}

}
