package org.ClAssignateur.domain.groupe;

import java.util.ArrayList;

import java.util.List;

public class Groupe {

	private Employe organisateur;
	private Employe responsable;
	private List<Employe> participants;
	
	public Groupe(Employe organisateur, Employe responsable, List<Employe> participants) {
		this.organisateur = organisateur;
		this.responsable = responsable;
		this.participants = participants;
	}

	public Employe getOrganisateur() {
		return this.organisateur;
	}

	public Employe getResponsable() {
		return this.responsable;
	}

	public int getNbParticipants() {
		return participants.size();
	}

	public List<Employe> getParticipants() {
		return participants;
	}

}
