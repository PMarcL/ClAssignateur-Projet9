package org.ClAssignateur.domaine.notification;

import org.ClAssignateur.domaine.groupe.Employe;

public interface NotificationStrategie {

	public void notifier(String contenu, Employe destinataire) throws NotificationException;
}
