package org.ClAssignateur.domain;

import org.w3c.dom.ranges.RangeException;

public class Demande {
	private final int NOMBRE_PARTICIPANTS_MINIMUM = 0;

	private int nbParticipant;
	private String organisateur;
	private Priorite priorite;

	public Demande(int nombreParticipant, String organisateur) {
		validerNombreParticipant(nombreParticipant);

		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
		this.priorite = Priorite.basse();
	}

	public Demande(int nombreParticipant, String organisateur, Priorite priorite) {
		validerNombreParticipant(nombreParticipant);

		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
		this.priorite = priorite;
	}

	private void validerNombreParticipant(int nombreParticipant) {
		if (nombreParticipant <= NOMBRE_PARTICIPANTS_MINIMUM)
			throw new RangeException((short) NOMBRE_PARTICIPANTS_MINIMUM,
					"Le nombre de participants doit être supérieur au minimum de participants requis.");
	}

	public int getNbParticipants() {
		return this.nbParticipant;
	}

	public String getOrganisateur() {
		return this.organisateur;
	}

	public boolean estPlusPrioritaire(Demande autreDemande) {
		return this.priorite.estPlusPrioritaire(autreDemande.priorite);
	}

	public boolean estAutantPrioritaire(Demande autreDemande) {
		return (!this.priorite.estPlusPrioritaire(autreDemande.priorite) && !autreDemande.priorite
				.estPlusPrioritaire(priorite));
	}
}
