package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import org.ClAssignateur.domaine.groupe.Employe;
import org.ClAssignateur.domaine.notification.NotificationException;
import org.ClAssignateur.domaine.notification.NotificationStrategie;

public class NotificationSilencieuse implements NotificationStrategie {

	@Override
	public void notifier(String contenu, Employe destinataire) throws NotificationException {
		// Aucune notification pour les tests
	}

}
