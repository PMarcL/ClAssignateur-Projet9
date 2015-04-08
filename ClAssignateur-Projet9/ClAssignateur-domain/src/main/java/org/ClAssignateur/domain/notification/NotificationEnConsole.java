package org.ClAssignateur.domain.notification;

import org.ClAssignateur.domain.groupe.Employe;

public class NotificationEnConsole implements NotificationStrategie {

	public void notifier(String contenu, Employe destinataire) throws NotificationException {
		System.out.println("Message envoyé à : " + destinataire + "\nContenu du message:\n" + contenu);
	}

}
