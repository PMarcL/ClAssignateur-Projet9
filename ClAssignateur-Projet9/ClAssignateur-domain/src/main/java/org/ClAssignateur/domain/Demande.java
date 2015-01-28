package org.ClAssignateur.domain;

import java.util.Calendar;

import org.w3c.dom.ranges.RangeException;

public class Demande {
	private static final int NOMBRE_PARTICIPANTS_MINIMUM = 0;
	
	private Calendar debut;
	private Calendar fin;
	private int nbParticipant;
	private Organisateur organisateur;
	
	public Demande(Calendar dateDebut, Calendar dateFin, int nombreParticipant, Organisateur organisateur) {
		if(!dateDebut.before(dateFin))
			throw new RangeException((short) 1, "La date de début doit être inférieur à la date de fin.");
		
		if(nombreParticipant <= NOMBRE_PARTICIPANTS_MINIMUM)
			throw new RangeException((short) NOMBRE_PARTICIPANTS_MINIMUM, "Le nombre de participants doit être supérieur au minimum de participants requis.");
		
		this.debut = dateDebut;
		this.fin = dateFin;
		this.nbParticipant = nombreParticipant;
		this.organisateur = organisateur;
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

	public Organisateur getOrganisateur() {
		return this.organisateur;
	}

	public boolean estEnConflitAvec(Demande demandeAVerifier) {
		boolean finEstAvantDebut = this.getFin().before(demandeAVerifier.getDebut());
		boolean debutEstApresFin = this.getDebut().after(demandeAVerifier.getFin());
		
		return !(finEstAvantDebut || debutEstApresFin);
	}

}
