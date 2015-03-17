package org.ClAssignateur.domain.demandes;

import java.util.UUID;

import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.groupe.Groupe;
import org.ClAssignateur.domain.salles.Salle;
import java.util.List;

public class Demande {

	private Groupe groupe;
	private Priorite priorite;
	private String titre;
	private Salle salleAssignee;
	private UUID id;

	public Demande(Groupe groupe, String titre, Priorite priorite) {
		this.id = UUID.randomUUID();
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.salleAssignee = null;
	}

	public Demande(Groupe groupe, String titre) {
		this.id = UUID.randomUUID();
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
		this.salleAssignee = null;
	}

	public Demande(UUID id, Groupe groupe, String titre, Priorite priorite) {
		this.id = id;
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.salleAssignee = null;
	}

	public Demande(UUID id, Groupe groupe, String titre) {
		this.id = id;
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
		this.salleAssignee = null;
	}

	public Groupe getGroupe() {
		return this.groupe;
	}

	public UUID getID() {
		return this.id;
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

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Demande)) {
			return false;
		} else {
			Demande autreDemande = (Demande) other;
			return this.getID().equals(autreDemande.getID());
		}
	}
}
