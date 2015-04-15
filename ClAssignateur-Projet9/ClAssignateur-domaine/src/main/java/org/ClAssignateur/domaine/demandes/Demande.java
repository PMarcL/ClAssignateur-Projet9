package org.ClAssignateur.domaine.demandes;

import org.ClAssignateur.domaine.groupe.courriel.AdresseCourriel;

import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.groupe.Employe;
import org.ClAssignateur.domaine.groupe.Groupe;
import org.ClAssignateur.domaine.salles.Salle;
import java.util.UUID;
import java.util.List;

public class Demande {

	public enum StatutDemande {
		EN_ATTENTE, ACCEPTEE, REFUSEE
	}

	private static int nombreDemandesCrees;

	private Groupe groupe;
	private Priorite priorite;
	private String titre;
	private Salle salleAssignee;
	private UUID id;
	private StatutDemande etat;
	private int estampille;

	private static int genererEstampille() {
		return ++nombreDemandesCrees;
	}

	public Demande(Groupe groupe, String titre, Priorite priorite) {
		this.id = UUID.randomUUID();
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		ajouterEstampille();
	}

	public Demande(Groupe groupe, String titre) {
		this.id = UUID.randomUUID();
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		ajouterEstampille();
	}

	public Demande(UUID id, Groupe groupe, String titre, Priorite priorite) {
		this.id = id;
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		ajouterEstampille();
	}

	public Demande(UUID id, Groupe groupe, String titre) {
		this.id = id;
		this.groupe = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		ajouterEstampille();
	}

	private void ajouterEstampille() {
		this.estampille = Demande.genererEstampille();
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
		return this.priorite.equals(autreDemande.priorite);
	}

	public void placerReservation(Salle salleAssignee) {
		this.etat = StatutDemande.ACCEPTEE;
		this.salleAssignee = salleAssignee;
	}

	public void annulerReservation() {
		this.etat = StatutDemande.REFUSEE;
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

	public Salle getSalleAssignee() {
		return this.salleAssignee;
	}

	public AdresseCourriel getCourrielOrganisateur() {
		return this.getOrganisateur().courriel;
	}

	@Override
	public boolean equals(Object autreObjet) {
		if (autreObjet == null) {
			return false;
		}
		if (!(autreObjet instanceof Demande)) {
			return false;
		} else {
			Demande autreDemande = (Demande) autreObjet;
			return this.getID().equals(autreDemande.getID());
		}
	}

	public StatutDemande getEtat() {
		return this.etat;
	}

	public int getNiveauPriorite() {
		return this.priorite.getNiveauPriorite();
	}

	public boolean estAnterieureA(Demande demande) {
		return this.estampille < demande.estampille;
	}

}
