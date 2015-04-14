package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.notification.NotificationException;
import org.ClAssignateur.domain.notification.NotificationStrategie;

public class NotificationSilencieuse implements NotificationStrategie {

	@Override
	public void notifier(String contenu, Employe destinataire) throws NotificationException {
		// Aucune notification pour les tests
	}

}
