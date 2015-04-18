package org.ClAssignateur.domaine.salles;

import javax.persistence.Column;

import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Salle {

	@Id
	private final String nom;
	@Column
	private final int capacite;

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
