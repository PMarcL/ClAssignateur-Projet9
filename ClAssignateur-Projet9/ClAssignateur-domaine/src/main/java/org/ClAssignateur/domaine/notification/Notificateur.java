package org.ClAssignateur.domaine.notification;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.groupe.Employe;
import org.ClAssignateur.domaine.salles.Salle;

public class Notificateur {

	private NotificationStrategie notificationStrategie;

	public Notificateur(NotificationStrategie notificationStrategie) {
		this.notificationStrategie = notificationStrategie;
	}

	public void notifierAnnulation(Demande demandeAAnnuler) {
		String contenu = "La demande nommée:" + demandeAAnnuler.getTitre() + " à été annulée.";

		notifierDirigeants(contenu, demandeAAnnuler);
		notifierParticipants(contenu, demandeAAnnuler);
	}

	public void notifierSucces(Demande demandeAvecSalleDisponible, Salle salleDisponible) {
		String contenu = "La salle: " + salleDisponible.getNom() + " a été réservée avec succès.";
		notifierDirigeants(contenu, demandeAvecSalleDisponible);
	}

	public void notifierEchec(Demande demandeNePouvantPasEtreAssignee) {
		String contenu = "Aucune salle n'a pu être assignée avec votre demande.";
		notifierDirigeants(contenu, demandeNePouvantPasEtreAssignee);
	}

	private void notifierParticipants(String contenu, Demande demande) {
		for (Employe participant : demande.getParticipants()) {
			this.notificationStrategie.notifier(contenu, participant);
		}
	}

	private void notifierDirigeants(String contenu, Demande demande) {
		this.notificationStrategie.notifier(contenu, demande.getOrganisateur());
		this.notificationStrategie.notifier(contenu, demande.getResponsable());
	}
}
