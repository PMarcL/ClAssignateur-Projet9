package org.ClAssignateur.contexte.developpement;

import java.util.ArrayList;
import java.util.UUID;

import org.ClAssignateur.contexte.Contexte;
import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleOptimaleStrategie;
import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.persistance.EnMemoireDemandeEntrepot;
import org.ClAssignateur.persistance.EnMemoireSallesEntrepot;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.services.reservations.minuterie.MinuterieTimerJavaStandard;

public class ContexteDeveloppement extends Contexte {

	private final int CAPACITE_SALLE_PAR_DEFAUT = 99;

	private SallesEntrepot sallesEntrepot;
	private ConteneurDemandes conteneurDemandes;

	public ContexteDeveloppement() {
		this.sallesEntrepot = new EnMemoireSallesEntrepot();
		this.conteneurDemandes = new ConteneurDemandes(new EnMemoireDemandeEntrepot(), new EnMemoireDemandeEntrepot());
	}

	@Override
	protected void enregistrerServices() {
		LocalisateurServices.getInstance().enregistrer(ConteneurDemandes.class, this.conteneurDemandes);
		LocalisateurServices.getInstance().enregistrer(Minuterie.class, new MinuterieTimerJavaStandard());
		LocalisateurServices.getInstance().enregistrer(AssignateurSalle.class, creerAssignateurSalle());
	}

	private AssignateurSalle creerAssignateurSalle() {
		AssignateurSalle assignateurSalle = new AssignateurSalle(this.conteneurDemandes, this.sallesEntrepot,
				creerNotificateur(), new SelectionSalleOptimaleStrategie());
		return assignateurSalle;
	}

	private Notificateur creerNotificateur() {
		return new Notificateur(new NotificationStrategieConsole());
	}

	@Override
	protected void injecterDonnees() {
		ajouterSalles();
		ajouterDemandes();
	}

	private void ajouterSalles() {
		this.sallesEntrepot.persister(new Salle(CAPACITE_SALLE_PAR_DEFAUT, "PLT-3904"));
		this.sallesEntrepot.persister(new Salle(CAPACITE_SALLE_PAR_DEFAUT, "PLT-2551"));
		this.sallesEntrepot.persister(new Salle(CAPACITE_SALLE_PAR_DEFAUT, "VCH-2860"));
	}

	private void ajouterDemandes() {
		InformationsContact organisateur = new InformationsContact("organisateur@hotmail.com");
		InformationsContact responsable = new InformationsContact("responsable@hotmail.com");
		ContactsReunion groupe = new ContactsReunion(organisateur, responsable, new ArrayList<InformationsContact>());
		UUID id = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		int nombreParticipants = 10;
		Demande demandeEnAttente = new Demande(id, nombreParticipants, groupe, "Demande demo", Priorite.basse());

		Demande demandeAssignee = new Demande(UUID.randomUUID(), nombreParticipants, groupe, "Demande demo2",
				Priorite.basse());
		Salle salle = new Salle(15, "A15");
		demandeAssignee.placerReservation(salle);

		conteneurDemandes.mettreDemandeEnAttente(demandeEnAttente);
		conteneurDemandes.archiverDemande(demandeAssignee);
	}

}
