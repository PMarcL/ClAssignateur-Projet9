package org.ClAssignateur.domain;

import org.w3c.dom.ranges.RangeException;

import java.util.Date;

public class Demande {
	private static final int NOMBRE_PARTICIPANTS_MINIMUM = 0;

	private Date debut;
	private Date fin;
	private int nbParticipant;
	private Organisateur organisateur;

	public Demande(Date debut, Date fin, int nombreParticipant,
			Organisateur organisateur) {
		if (!debut.before(fin))
			throw new RangeException((short) 1,
					"La date de début doit être inférieur à la date de fin.");

		if (nombreParticipant <= NOMBRE_PARTICIPANTS_MINIMUM)
			throw new RangeException(
					(short) NOMBRE_PARTICIPANTS_MINIMUM,
					"Le nombre de participants doit être supérieur au minimum de participants requis.");

		this.debut = debut;
		this.fin = fin;
		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
	}

	public Date getDebut() {
		return this.debut;
	}

	public Date getFin() {
		return this.fin;
	}

	public int getNbParticipant() {
		return this.nbParticipant;
	}

	public Organisateur getOrganisateur() {
		return this.organisateur;
	}

	public boolean estEnConflitAvec(Demande demande) {
		if (demande == this)
			return true;
		return false;
	}

}
