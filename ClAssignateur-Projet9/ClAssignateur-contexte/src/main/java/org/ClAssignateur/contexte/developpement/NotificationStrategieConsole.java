package org.ClAssignateur.contexte.developpement;

import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.notification.NotificationException;
import org.ClAssignateur.domaine.notification.NotificationStrategie;

public class NotificationStrategieConsole implements NotificationStrategie {

	@Override
	public void notifier(String contenu, InformationsContact destinataire) throws NotificationException {
		System.out.println("Envoi d'une notification à " + destinataire.getAdresseCourriel());
		System.out.println("Contenu du message: " + contenu);
	}

}
