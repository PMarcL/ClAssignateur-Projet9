package org.ClAssignateur.domain;

public class Demande {

	private Groupe groupe;
	private Priorite priorite;
	private String titre;

	public Demande(Groupe groupe, String titre, Priorite priorite) {
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = priorite;
	}

	public Demande(Groupe groupe, String titre) {
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public String getTitre() {
		return this.titre;
	}

	public int getNbParticipant() {
		int nombreDeParticipant = this.groupe.getNbParticipant();
		return nombreDeParticipant;
	}

	public boolean estPlusPrioritaire(Demande autreDemande) {
		return this.priorite.estPlusPrioritaire(autreDemande.priorite);
	}

	public boolean aLeMemeNiveauDePriorite(Demande autreDemande) {
		return (!this.priorite.estPlusPrioritaire(autreDemande.priorite) && !autreDemande.priorite
				.estPlusPrioritaire(priorite));
	}

	public boolean equals(Demande demande) {
		return this.titre.equals(demande.getTitre()) && this.groupe.equals(demande.getGroupe())
				&& this.aLeMemeNiveauDePriorite(demande);
	}

}
