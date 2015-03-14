package org.ClAssignateur.domain;

public class Salle {

	private String nom;
	private int capacite;

	public Salle(int capacite, String nom) {
		this.capacite = capacite;
		this.nom = nom;
	}

	public boolean peutAccueillir(int nbParticipants) {
		return this.capacite >= nbParticipants;
	}

	public String getNom() {
		return this.nom;
	}
}
