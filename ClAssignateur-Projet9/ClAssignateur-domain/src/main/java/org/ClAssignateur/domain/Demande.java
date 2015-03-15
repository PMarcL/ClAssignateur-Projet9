package org.ClAssignateur.domain;

import java.util.List;

import java.util.ArrayList;

public class Demande {

	public enum EtatDemande {
		EN_ATTENTE, ANNULEE, ASSIGNEE
	}

	private Groupe groupe;
	private Priorite priorite;
	private String titre;
	private EtatDemande etat;
	private ArrayList<Salle> reservations = new ArrayList<Salle>();

	public Demande(Groupe groupe, String titre, Priorite priorite) {
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.etat = EtatDemande.EN_ATTENTE;
	}

	public Demande(Groupe groupe, String titre) {
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
		this.etat = EtatDemande.EN_ATTENTE;
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public boolean estPlusPrioritaire(Demande autreDemande) {
		return this.priorite.estPlusPrioritaire(autreDemande.priorite);
	}

	public boolean aLeMemeNiveauDePriorite(Demande autreDemande) {
		return (!this.priorite.estPlusPrioritaire(autreDemande.priorite) && !autreDemande.priorite
				.estPlusPrioritaire(priorite));
	}

	public void placerReservation(Salle nouvelleReservation) {
		reservations.add(nouvelleReservation);
		etat = EtatDemande.ASSIGNEE;
	}

	public void annulerReservation() {
		this.etat = EtatDemande.ANNULEE;
	}

	public boolean estAssignee() {
		return this.etat == EtatDemande.ASSIGNEE;
	}

	public boolean estEnAttente() {
		return this.etat == EtatDemande.EN_ATTENTE;
	}

	public boolean estAnnulee() {
		return this.etat == EtatDemande.ANNULEE;
	}

	public String getTitre() {
		return this.titre;
	}

	public int getNbParticipants() {
		int nombreDeParticipant = this.groupe.getNbParticipant();
		return nombreDeParticipant;
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

	public List<Employe> getParticipants() {
		return groupe.getParticipants();
	}

}
