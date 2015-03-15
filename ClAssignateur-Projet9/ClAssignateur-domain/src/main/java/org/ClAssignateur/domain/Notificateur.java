package org.ClAssignateur.domain;

public class Notificateur {

	private NotificationStrategie notifictionStrategie;

	public Notificateur(NotificationStrategie notificationStrategie) {
		this.notifictionStrategie = notificationStrategie;
	}

	public void notifierAnnulation(Demande demandeAAnnuler) {

	}

	public void notifierSucces(Demande demandeAvecSalleDisponible, Salle salleDisponible) {
		String contenu = "La salle: " + salleDisponible.getNom() + " a été réservée avec succès";

		this.notifictionStrategie.notifier(contenu, demandeAvecSalleDisponible.getOrganisateur());
		this.notifictionStrategie.notifier(contenu, demandeAvecSalleDisponible.getResponsable());
	}

	public void notifierEchec(Demande demandeNePouvantPasEtreAssignee) {
		this.notifictionStrategie.notifier("", demandeNePouvantPasEtreAssignee.getOrganisateur());
		this.notifictionStrategie.notifier("", demandeNePouvantPasEtreAssignee.getResponsable());
	}
}
