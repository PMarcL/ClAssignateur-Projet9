package org.ClAssignateur.domain;

import java.util.ArrayList;

public class Salle {

	private String nom;
	private int capacite;
	private ArrayList<Demande> reservations;

	public Salle(String nomRecu, int capaciteRecu) {
		nom = nomRecu;
		capacite = capaciteRecu;
		reservations = new ArrayList<Demande>();
	}

	public String getNom() {
		return nom;
	}

	public boolean peutAccueillir(int nbParticipants) {
		return capacite >= nbParticipants;
	}

	public void placerReservation(Demande nouvelleReservation) {
		reservations.add(nouvelleReservation);
	}

	public int getNbReservation() {
		return reservations.size();

	}

}
