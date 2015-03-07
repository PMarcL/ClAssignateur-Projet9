package org.ClAssignateur.domain;

import java.util.ArrayList;

public class Demande {

	private Groupe groupe;
	private Priorite priorite;
	private StrategieNotificationFactory strategieNotificationFactory;
	private ArrayList<Salle> reservations = new ArrayList<Salle>();

	public Demande(Groupe groupe, Priorite priorite, StrategieNotificationFactory strategieNotificationFactory) {
		this.groupe = groupe;
		this.priorite = priorite;
		this.strategieNotificationFactory = strategieNotificationFactory;
	}

	public Demande(Groupe groupe, StrategieNotificationFactory strategieNotificationFactory) {
		this.groupe = groupe;
		this.priorite = Priorite.basse();
		this.strategieNotificationFactory = strategieNotificationFactory;
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

	public void placerReservation(Salle nouvelleReservation) {
		reservations.add(nouvelleReservation);

		StrategieNotification strategieNotification = strategieNotificationFactory.creerStrategieNotification();
		MessageNotification message = new MessageNotificationSuccess(nouvelleReservation);

		strategieNotification.notifier(message, groupe.getOrganisateur());
		strategieNotification.notifier(message, groupe.getResponsable());
	}

	public int getNbReservation() {
		return reservations.size();
	}

	public void signalerAucuneDemandeCorrespondante() {
		StrategieNotification strategieNotification = strategieNotificationFactory.creerStrategieNotification();
		MessageNotification message = new MessageNotificationEchec();

		strategieNotification.notifier(message, groupe.getOrganisateur());
		strategieNotification.notifier(message, groupe.getResponsable());
	}

}
