package org.ClAssignateur.domain.notification;

import org.ClAssignateur.domain.groupe.Employe;

public interface NotificationStrategie {

	public void notifier(String contenu, Employe destinataire) throws NotificationException;
}
