package org.ClAssignateur.domain;

import java.util.Calendar;
import org.w3c.dom.ranges.RangeException;

public class Demande {
	private static final int NOMBRE_PARTICIPANTS_MINIMUM = 0;
	public static final int PRIORITE_MINIMAL = 1;
	public static final int PRIORITE_MAXIMALE = 5;

	private Calendar debut;
	private Calendar fin;
	private int nbParticipant;
	private String organisateur;
	private int priorite;

	public Demande(Calendar dateDebut, Calendar dateFin, int nombreParticipant,
			String organisateur) {

		validerDates(dateDebut, dateFin);
		validerNombreParticipant(nombreParticipant);

		this.debut = dateDebut;
		this.fin = dateFin;
		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
		this.priorite = PRIORITE_MINIMAL;
	}

	public Demande(Calendar dateDebut, Calendar dateFin, int nombreParticipant,
			String organisateur, int priorite) {

		validerDates(dateDebut, dateFin);
		validerNombreParticipant(nombreParticipant);
		validerPriorite(priorite);

		this.debut = dateDebut;
		this.fin = dateFin;
		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
		this.priorite = priorite;
	}

	private void validerPriorite(int priorite) {
		String messageErreur = String.format(
				"La priorite doit être entre %d et %d inclusivement",
				PRIORITE_MINIMAL, PRIORITE_MAXIMALE);

		if (priorite < PRIORITE_MINIMAL)
			throw new RangeException((short) PRIORITE_MINIMAL, messageErreur);

		if (priorite > PRIORITE_MAXIMALE)
			throw new RangeException((short) PRIORITE_MAXIMALE, messageErreur);
	}

	private void validerDates(Calendar dateDebut, Calendar dateFin) {
		if (!dateDebut.before(dateFin))
			throw new RangeException((short) 1,
					"La date de début doit être inférieur à la date de fin.");
	}

	private void validerNombreParticipant(int nombreParticipant) {
		if (nombreParticipant <= NOMBRE_PARTICIPANTS_MINIMUM)
			throw new RangeException(
					(short) NOMBRE_PARTICIPANTS_MINIMUM,
					"Le nombre de participants doit être supérieur au minimum de participants requis.");
	}

	public Calendar getDebut() {
		return this.debut;
	}

	public Calendar getFin() {
		return this.fin;
	}

	public int getNbParticipant() {
		return this.nbParticipant;
	}

	public String getOrganisateur() {
		return this.organisateur;
	}

	public boolean estEnConflitAvec(Demande demandeAVerifier) {
		boolean finEstAvantDebut = this.getFin().before(
				demandeAVerifier.getDebut());
		boolean debutEstApresFin = this.getDebut().after(
				demandeAVerifier.getFin());

		return !(finEstAvantDebut || debutEstApresFin);
	}

	public int getPriorite() {
		return this.priorite;
	}

}
