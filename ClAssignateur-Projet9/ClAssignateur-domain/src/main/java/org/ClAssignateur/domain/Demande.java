package org.ClAssignateur.domain;

import org.w3c.dom.ranges.RangeException;

import java.util.Date;

public class Demande {
	private static final int NOMBRE_PARTICIPANTS_MINIMUM = 0;
	
	private Date _debut;
	private Date _fin;
	private int _nbParticipant;
	private Organisateur _organisateur;
	
	public Demande(Date debut, Date fin, int nombreParticipant, Organisateur organisateur) {
		if(!debut.before(fin))
			throw new RangeException((short) 1, "La date de début doit être inférieur à la date de fin.");
		
		if(nombreParticipant <= NOMBRE_PARTICIPANTS_MINIMUM)
			throw new RangeException((short) NOMBRE_PARTICIPANTS_MINIMUM, "Le nombre de participants doit être supérieur au minimum de participants requis.");
		
		this._debut = debut;
		this._fin = fin;
		this._nbParticipant = nombreParticipant;
		this._organisateur = organisateur;
	}

	public Date GetDebut() {
		return this._debut;
	}

	public Date GetFin() {
		return this._fin;
	}

	public int GetNbParticipant() {
		return this._nbParticipant;
	}

	public Organisateur GetOrganisateur() {
		return this._organisateur;
	}

}
