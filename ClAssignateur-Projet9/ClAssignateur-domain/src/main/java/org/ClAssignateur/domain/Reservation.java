package org.ClAssignateur.domain;

public class Reservation {

	private Demande demande;
	private Salle salle;

	public Reservation(Demande demande, Salle salle) {
		this.demande = demande;
		this.salle = salle;
	}

	public Demande getDemande() {
		return demande;
	}

	public Salle getSalle() {
		return salle;
	}

	public boolean concerneDemande(Demande demande) {
		return this.demande.equals(demande);
	}

}
