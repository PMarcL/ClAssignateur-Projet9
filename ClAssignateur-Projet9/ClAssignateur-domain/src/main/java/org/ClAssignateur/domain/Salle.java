package org.ClAssignateur.domain;

public class Salle {

	private int capacite;

	public Salle(int capacite) {
		this.capacite = capacite;
	}

	public boolean peutAccueillir(int nbParticipants) {
		return capacite >= nbParticipants;
	}
}
