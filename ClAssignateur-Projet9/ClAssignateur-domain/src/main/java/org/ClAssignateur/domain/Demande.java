package org.ClAssignateur.domain;

import java.util.ArrayList;

public class Demande {

	private Groupe groupe;
	private Priorite priorite;
	private StrategieNotification strategieNotification;
	private ArrayList<Salle> reservations = new ArrayList<Salle>();

	public Demande(Groupe groupe, Priorite priorite, StrategieNotification strategieNotification) {
		this.groupe = groupe;
		this.priorite = priorite;
		this.strategieNotification = strategieNotification;
	}

	public Demande(Groupe groupe, StrategieNotification strategieNotification) {
		this.groupe = groupe;
		this.priorite = Priorite.basse();
		this.strategieNotification = strategieNotification;
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public int getNbParticipant() {
		int nombreDeParticipant = this.groupe.getNbParticipant();
		return nombreDeParticipant;
	}

	public boolean estPlusPrioritaire(Demande autreDemande) {
		return this.priorite.estPlusPrioritaire(autreDemande.priorite);
	}

	public boolean estAutantPrioritaire(Demande autreDemande) {
		return (!this.priorite.estPlusPrioritaire(autreDemande.priorite) && !autreDemande.priorite
				.estPlusPrioritaire(priorite));
	}

	public int getNbReservation() {
		return reservations.size();
	}

	public void placerReservation(Salle nouvelleReservation) {
		reservations.add(nouvelleReservation);
		envoyerNotificationSucces(nouvelleReservation);
	}

	public void signalerAucuneDemandeCorrespondante() {
		MessageNotification message = new MessageNotificationEchec();

		strategieNotification.notifier(message, groupe.getOrganisateur());
		strategieNotification.notifier(message, groupe.getResponsable());
	}

	private void envoyerNotificationSucces(Salle salleReserve) {
		MessageNotification message = new MessageNotificationSucces(salleReserve);

		strategieNotification.notifier(message, groupe.getOrganisateur());
		strategieNotification.notifier(message, groupe.getResponsable());
	}
}
