package org.ClAssignateur.domain;

import org.w3c.dom.ranges.RangeException;

public class Demande {
	private final int NOMBRE_PARTICIPANTS_MINIMUM = 0;

	private int nbParticipant;
	private Employe organisateur;
	private Priorite priorite;
	private StrategieNotificationFactory strategieNotificationFactory;

	public Demande(int nombreParticipant, Employe organisateur, Priorite priorite,
			StrategieNotificationFactory strategieNotificationFactory) {
		validerNombreParticipant(nombreParticipant);

		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
		this.priorite = priorite;
		this.strategieNotificationFactory = strategieNotificationFactory;
	}

	public Demande(int nombreParticipant, Employe organisateur,
			StrategieNotificationFactory strategieNotificationFactory) {
		validerNombreParticipant(nombreParticipant);

		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
		this.priorite = Priorite.basse();
		this.strategieNotificationFactory = strategieNotificationFactory;
	}

	private void validerNombreParticipant(int nombreParticipant) {
		if (nombreParticipant <= NOMBRE_PARTICIPANTS_MINIMUM)
			throw new RangeException((short) NOMBRE_PARTICIPANTS_MINIMUM,
					"Le nombre de participants doit être supérieur au minimum de participants requis.");
	}

	public int getNbParticipant() {
		return this.nbParticipant;
	}

	public Employe getOrganisateur() {
		return this.organisateur;
	}

	public boolean estPlusPrioritaire(Demande autreDemande) {
		return this.priorite.estPlusPrioritaire(autreDemande.priorite);
	}

	public boolean estAutantPrioritaire(Demande autreDemande) {
		return (!this.priorite.estPlusPrioritaire(autreDemande.priorite) && !autreDemande.priorite
				.estPlusPrioritaire(priorite));
	}

	public void notifierEchecAssignation() {
		StrategieNotification strategieNotification = this.strategieNotificationFactory.creerStrategieNotification();
		strategieNotification.notifierEchecAssignation(this.getOrganisateur());
	}

	public void notifierAssignation(Salle salleAssigne) {
		StrategieNotification strategieNotification = this.strategieNotificationFactory.creerStrategieNotification();
		strategieNotification.notifierAssignation(salleAssigne, this.getOrganisateur());
	}
}
