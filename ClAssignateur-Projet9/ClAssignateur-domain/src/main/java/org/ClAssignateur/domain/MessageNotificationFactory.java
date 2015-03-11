package org.ClAssignateur.domain;

public class MessageNotificationFactory {

	public MessageNotification genereNotificationSucces(Salle salleAssigne) {
		MessageNotification messageSucces = new MessageNotificationSucces(salleAssigne);
		return messageSucces;
	}

	public MessageNotification genereNotificationEchec() {
		MessageNotification messageEchec = new MessageNotificationEchec();
		return messageEchec;
	}

}
