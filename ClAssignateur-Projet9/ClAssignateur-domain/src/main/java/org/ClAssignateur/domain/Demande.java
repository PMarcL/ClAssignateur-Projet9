package org.ClAssignateur.domain;

import java.util.List;

public class Demande {

	private Groupe groupe;
	private Priorite priorite;
	private String titre;
	private Salle salleAssignee;

	public Demande(Groupe groupe, String titre, Priorite priorite) {
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.salleAssignee = null;
	}

	public Demande(Groupe groupe, String titre) {
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
		this.salleAssignee = null;
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public boolean estPlusPrioritaire(Demande autreDemande) {
		return this.priorite.estPlusPrioritaire(autreDemande.priorite);
	}

	public boolean estAussiPrioritaire(Demande autreDemande) {
		return (!this.priorite.estPlusPrioritaire(autreDemande.priorite) && !autreDemande.priorite
				.estPlusPrioritaire(priorite));
	}

	public void placerReservation(Salle salleAssignee) {
		this.salleAssignee = salleAssignee;
	}

	public void annulerReservation() {
		this.salleAssignee = null;
	}

	public boolean estAssignee() {
		return this.salleAssignee != null;
	}

	public String getTitre() {
		return this.titre;
	}

	public int getNbParticipants() {
		int nombreDeParticipants = this.groupe.getNbParticipants();
		return nombreDeParticipants;
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
