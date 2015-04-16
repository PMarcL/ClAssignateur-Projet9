package org.ClAssignateur.domaine.notification;

import org.ClAssignateur.domaine.contacts.InformationsContact;

public interface NotificationStrategie {

	public void notifier(String contenu, InformationsContact destinataire) throws NotificationException;
}
