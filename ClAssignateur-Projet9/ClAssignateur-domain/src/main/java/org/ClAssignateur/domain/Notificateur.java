package org.ClAssignateur.domain;

public class Notificateur {

	private NotificationStrategie notifictionStrategie;

	public Notificateur(NotificationStrategie notificationStrategie) {
		this.notifictionStrategie = notificationStrategie;
	}

	public void notifierAnnulation(Demande demandeAAnnuler) {
		String contenu = "La demande nommée:" + demandeAAnnuler.getTitre() + " à été annulée.";

		notifierDirigeants(contenu, demandeAAnnuler);

		for (Employe participant : demandeAAnnuler.getParticipants()) {
			this.notifictionStrategie.notifier(contenu, participant);
		}
	}

	public void notifierSucces(Demande demandeAvecSalleDisponible, Salle salleDisponible) {
		String contenu = "La salle: " + salleDisponible.getNom() + " a été réservée avec succès.";
		notifierDirigeants(contenu, demandeAvecSalleDisponible);
	}

	public void notifierEchec(Demande demandeNePouvantPasEtreAssignee) {
		String contenu = "Aucune salle n'a pu être assignée avec votre demande.";
		notifierDirigeants(contenu, demandeNePouvantPasEtreAssignee);
	}

	private void notifierDirigeants(String contenu, Demande demande) {
		this.notifictionStrategie.notifier(contenu, demande.getOrganisateur());
		this.notifictionStrategie.notifier(contenu, demande.getResponsable());
	}
}
