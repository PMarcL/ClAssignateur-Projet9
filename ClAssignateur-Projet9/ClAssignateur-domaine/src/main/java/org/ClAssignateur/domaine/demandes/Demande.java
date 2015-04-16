package org.ClAssignateur.domaine.demandes;

import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;

import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.salles.Salle;
import java.util.UUID;
import java.util.List;

public class Demande {

	public enum StatutDemande {
		EN_ATTENTE, ACCEPTEE, REFUSEE
	}

	private static int nombreDemandesCrees;

	private ContactsReunion contacts;
	private Priorite priorite;
	private String titre;
	private Salle salleAssignee;
	private UUID id;
	private StatutDemande etat;
	private int estampille;

	private static int genererEstampille() {
		return ++nombreDemandesCrees;
	}

	public Demande(ContactsReunion groupe, String titre, Priorite priorite) {
		this.id = UUID.randomUUID();
		this.contacts = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		ajouterEstampille();
	}

	public Demande(ContactsReunion groupe, String titre) {
		this.id = UUID.randomUUID();
		this.contacts = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		ajouterEstampille();
	}

	public Demande(UUID id, ContactsReunion groupe, String titre, Priorite priorite) {
		this.id = id;
		this.contacts = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		ajouterEstampille();
	}

	public Demande(UUID id, ContactsReunion groupe, String titre) {
		this.id = id;
		this.contacts = groupe;
		this.titre = titre;
		this.priorite = Priorite.basse();
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		ajouterEstampille();
	}

	private void ajouterEstampille() {
		this.estampille = Demande.genererEstampille();
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
		return this.contacts.getNbParticipants();
	}

	public InformationsContact getOrganisateur() {
		return this.contacts.organisateur;
	}

	public InformationsContact getResponsable() {
		return this.contacts.responsable;
	}

	public List<InformationsContact> getParticipants() {
		return this.contacts.participants;
	}

	public Salle getSalleAssignee() {
		return this.salleAssignee;
	}

	public String getCourrielOrganisateur() {
		return this.getOrganisateur().getAdresseCourriel();
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
