package org.ClAssignateur.domain;

import java.util.ArrayList;

public class Salle {

	private int capacite;
	private ArrayList<Demande> reservations;

	public Salle(int capacite) {
		this.capacite = capacite;
		reservations = new ArrayList<Demande>();
	}

	public boolean peutAccueillir(int nbParticipants) {
		return capacite >= nbParticipants;
	}

	public float getPourcentageOccupation(int nbParticipants) {
		return (float) nbParticipants / capacite;
	}

	public void placerReservation(Demande nouvelleReservation) {
		reservations.add(nouvelleReservation);
	}

	public int getNbReservation() {
		return reservations.size();

	}
}
