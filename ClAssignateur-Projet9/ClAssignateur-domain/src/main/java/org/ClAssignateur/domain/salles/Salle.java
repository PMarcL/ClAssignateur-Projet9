package org.ClAssignateur.domain.salles;

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

	public float getTauxOccupation(int nbParticipants) {
		return (float) nbParticipants / this.capacite;
	}

	public String getNom() {
		return this.nom;
	}
}
