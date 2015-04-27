package org.ClAssignateur.testsAcceptationUtilisateur.fixtures;

import java.util.ArrayList;
import java.util.List;

import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;

public class DemandeConstructeur {

	private final int NOMBRE_PARTICIPANTS_PAR_DEFAUT = 25;
	private final InformationsContact ORGANISATEUR = new InformationsContact("organisateur@domaine.com");
	private final InformationsContact RESPONSABLE = new InformationsContact("responsable@domaine.com");
	private final String TITRE_PAR_DEFAUT = "Nouvelle réunion";
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.tresHaute();

	private int nbParticipants;
	private InformationsContact organisateur;
	private InformationsContact responsable;
	private List<InformationsContact> participants;
	private String titre;
	private Priorite priorite;

	public DemandeConstructeur() {
		this.nbParticipants = NOMBRE_PARTICIPANTS_PAR_DEFAUT;
		this.organisateur = ORGANISATEUR;
		this.responsable = RESPONSABLE;
		this.participants = new ArrayList<>();
		this.titre = TITRE_PAR_DEFAUT;
		this.priorite = PRIORITE_PAR_DEFAUT;
	}

	public DemandeConstructeur titre(String titre) {
		this.titre = titre;
		return this;
	}

	public DemandeConstructeur priorite(Priorite priorite) {
		this.priorite = priorite;
		return this;
	}

	public Demande construireDemande() {
		ContactsReunion contacts = creerContactsReunion();
		return new Demande(this.nbParticipants, contacts, this.titre, this.priorite);
	}

	private ContactsReunion creerContactsReunion() {
		return new ContactsReunion(this.organisateur, this.responsable, this.participants);
	}

}
