package org.ClAssignateur.domain;

import org.w3c.dom.ranges.RangeException;

public class Demande {
	private final int NOMBRE_PARTICIPANTS_MINIMUM = 0;
	private final int PRIORITE_MINIMAL = 1;
	private final int PRIORITE_MAXIMALE = 5;

	private int nbParticipant;
	private String organisateur;
	private int priorite;

	public Demande(int nombreParticipant, String organisateur) {
		validerNombreParticipant(nombreParticipant);

		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
		this.priorite = PRIORITE_MINIMAL;
	}

	public Demande(int nombreParticipant, String organisateur, int priorite) {
		validerNombreParticipant(nombreParticipant);
		validerPriorite(priorite);

		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
		this.priorite = priorite;
	}

	private void validerPriorite(int priorite) {
		String messageErreur = String.format("La priorite doit être entre %d et %d inclusivement", PRIORITE_MINIMAL,
				PRIORITE_MAXIMALE);

		if (priorite < PRIORITE_MINIMAL)
			throw new RangeException((short) PRIORITE_MINIMAL, messageErreur);

		if (priorite > PRIORITE_MAXIMALE)
			throw new RangeException((short) PRIORITE_MAXIMALE, messageErreur);
	}

	private void validerNombreParticipant(int nombreParticipant) {
		if (nombreParticipant <= NOMBRE_PARTICIPANTS_MINIMUM)
			throw new RangeException((short) NOMBRE_PARTICIPANTS_MINIMUM,
					"Le nombre de participants doit être supérieur au minimum de participants requis.");
	}

	public int getNbParticipant() {
		return this.nbParticipant;
	}

	public String getOrganisateur() {
		return this.organisateur;
	}

	public int getPriorite() {
		return this.priorite;
	}

}
