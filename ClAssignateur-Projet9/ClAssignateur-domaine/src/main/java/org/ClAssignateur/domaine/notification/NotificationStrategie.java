package org.ClAssignateur.domaine.notification;

import org.ClAssignateur.domaine.groupe.InformationsContact;

public interface NotificationStrategie {

	public void notifier(String contenu, InformationsContact destinataire) throws NotificationException;
}
