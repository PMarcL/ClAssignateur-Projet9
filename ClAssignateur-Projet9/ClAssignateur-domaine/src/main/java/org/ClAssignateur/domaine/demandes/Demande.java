package org.ClAssignateur.domaine.demandes;

import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.Entity;
import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.salles.Salle;
import java.util.UUID;
import java.util.List;

@Entity
public class Demande {

	public enum StatutDemande {
		EN_ATTENTE, ACCEPTEE, REFUSEE, ANNULEE
	}

	private static int nombreDemandesCrees;

	@Column
	private int nbParticipants;

	@OneToOne(cascade = CascadeType.ALL)
	private ContactsReunion contacts;

	@Column
	private Priorite priorite;

	@Column
	private String titre;

	@ManyToOne
	private Salle salleAssignee;

	@Id
	private UUID id;

	@Column
	private StatutDemande etat;

	@Column
	private int estampille;

	private static int genererEstampille() {
		return ++nombreDemandesCrees;
	}

	public Demande(int nombreParticipants, ContactsReunion groupe, String titre, Priorite priorite) {
		this.id = UUID.randomUUID();
		this.contacts = groupe;
		this.titre = titre;
		this.priorite = priorite;
		this.salleAssignee = null;
		this.etat = StatutDemande.EN_ATTENTE;
		this.nbParticipants = nombreParticipants;
		ajouterEstampille();
	}

	// TODO voir si encore utile
	public Demande(UUID id, int nombreParticipants, ContactsReunion groupe, String titre, Priorite priorite) {
		this.id = id;
		this.nbParticipants = nombreParticipants;
		this.contacts = groupe;
		this.titre = titre;
		this.priorite = priorite;
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
		this.etat = StatutDemande.ANNULEE;
		this.salleAssignee = null;
	}

	public boolean estAssignee() {
		return this.salleAssignee != null;
	}

	public String getTitre() {
		return this.titre;
	}

	public int getNbParticipants() {
		return this.nbParticipants;
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
			return this.id.equals(autreDemande.id);
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
