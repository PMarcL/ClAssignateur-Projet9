package org.ClAssignateur.domain;

import org.ClAssignateur.domain.groupe.Employe;

public interface NotificationStrategie {

	public void notifier(String contenu, Employe destinataire);
}
