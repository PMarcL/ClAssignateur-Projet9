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

	public int getCapacite() {
		return capacite;
	}

	public boolean estDisponible(Demande demandeAVerifier) {
		if (reservations.isEmpty())
			return true;
		for (Demande reservation : reservations) {
			if (demandeAVerifier.estEnConflitAvec(reservation))
				return false;
		}
		return true;
	}

	public void placerReservation(Demande nouvelleReservation) {
		if (estDisponible(nouvelleReservation) == false)
			throw new IllegalArgumentException("La salle est déjà réservée à cette date.");
		reservations.add(nouvelleReservation);
	}

	public void enleverReservation(Demande reservationAEnlever) {
		reservations.remove(reservationAEnlever);
	}

}
