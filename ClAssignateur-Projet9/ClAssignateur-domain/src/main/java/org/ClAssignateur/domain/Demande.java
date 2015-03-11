package org.ClAssignateur.domain;

import java.util.ArrayList;

public class Demande {

	private Groupe groupe;
	private Priorite priorite;
	private ArrayList<Salle> reservations = new ArrayList<Salle>();

	public Demande(Groupe groupe, Priorite priorite) {
		this.groupe = groupe;
		this.priorite = priorite;
	}

	public Demande(Groupe groupe) {
		this.groupe = groupe;
		this.priorite = Priorite.basse();
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public int getNbParticipant() {
		int nombreDeParticipant = this.groupe.getNbParticipant();
		return nombreDeParticipant;
	}

	public boolean estPlusPrioritaire(Demande autreDemande) {
		return this.priorite.estPlusPrioritaire(autreDemande.priorite);
	}

	public boolean estAutantPrioritaire(Demande autreDemande) {
		return (!this.priorite.estPlusPrioritaire(autreDemande.priorite) && !autreDemande.priorite
				.estPlusPrioritaire(priorite));
	}

	public int getNbReservation() {
		return reservations.size();
	}

	public Employe getOrganisateur() {
		return this.getGroupe().getOrganisateur();
	}

	public Employe getResponsable() {
		return this.getGroupe().getResponsable();
	}

	public void placerReservation(Salle nouvelleReservation) {
		reservations.add(nouvelleReservation);
	}

}
