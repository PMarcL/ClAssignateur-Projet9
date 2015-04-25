package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.notification.NotificationException;
import org.ClAssignateur.domaine.notification.NotificationStrategie;

public class NotificationStrategieSilencieuse implements NotificationStrategie {

	@Override
	public void notifier(String contenu, InformationsContact destinataire) throws NotificationException {
		// cette implémentation n'envoie aucune notification
	}

}
