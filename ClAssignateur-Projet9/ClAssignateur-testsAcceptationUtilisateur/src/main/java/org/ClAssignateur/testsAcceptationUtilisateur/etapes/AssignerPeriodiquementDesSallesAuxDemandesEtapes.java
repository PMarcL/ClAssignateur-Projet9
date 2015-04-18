package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleOptimaleStrategie;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.persistance.EnMemoireSallesEntrepot;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.EnMemoireDemandesEntrepotFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.MinuterieFake;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AssignerPeriodiquementDesSallesAuxDemandesEtapes {

	private final int NB_PARTICIPANTS = 10;
	private final int NB_DEMANDES = 5;
	private final String TITRE_DEMANDE = "Réunion de 15 minutes";
	private final InformationsContact ORGANISATEUR = new InformationsContact("organisateur@gmail.com");
	private final InformationsContact RESPONSABLE = new InformationsContact("responsable@gmail.com");
	private final ContactsReunion CONTACTS_REUNION = new ContactsReunion(ORGANISATEUR, RESPONSABLE,
			new ArrayList<InformationsContact>());

	private AssignateurSalle assignateurSalle;
	private AssignateurSalle assignateurMock;
	private ConteneurDemandes conteneurDemandes;
	private Demande demande;
	private Minuterie minuterie;
	private MinuterieFake minuterieFake;
	private Minute minute;
	private SallesEntrepot salles;
	private Salle salle;
	private ServiceReservationSalle serviceReservation;
	private ArrayList<Demande> demandesEnAttente;

	@BeforeScenario
	public void initialisation() {
		conteneurDemandes = mock(ConteneurDemandes.class);
		minuterie = mock(Minuterie.class);
		minuterieFake = new MinuterieFake();
		minute = mock(Minute.class);
		salles = new EnMemoireSallesEntrepot();
		assignateurMock = mock(AssignateurSalle.class);
		demandesEnAttente = new ArrayList<>();

		assignateurSalle = new AssignateurSalle(conteneurDemandes, salles, mock(Notificateur.class),
				new SelectionSalleOptimaleStrategie());
		serviceReservation = new ServiceReservationSalle(assignateurSalle, minuterie);
	}

	@Given("une liste de salle dont la première salle disponible est X")
	public void givenUneListeDeSalleDontLaPremiereSalleDisponibleEstX() {
		salle = new Salle(100, "PLT2770");
		salles.persister(salle);
	}

	@Given("une nouvelle demande")
	public void givenUneNouvelleDemande() {
		demande = new Demande(NB_PARTICIPANTS, CONTACTS_REUNION, TITRE_DEMANDE, Priorite.moyenne());
		assignateurSalle.ajouterDemande(demande);
		demandesEnAttente.add(demande);
		given(conteneurDemandes.obtenirDemandesEnAttenteOrdrePrioritaire()).willReturn(demandesEnAttente);
	}

	@Given("une fréquence par défaut")
	public void givenUneFrequenceParDefaut() {
	}

	@Given("une fréquence de traitement X")
	public void givenUneFrequenceDeTraitementX() {
		serviceReservation = new ServiceReservationSalle(assignateurMock, minuterieFake);
	}

	@When("les demandes sont toutes traitées")
	public void whenLesDemandesSontToutesTraitees() {
		assignateurSalle.lancerAssignation();
	}

	@When("la fréquence est modifiée")
	public void whenLaFrequenceEstMofidiee() {
		serviceReservation.setFrequence(minute);
	}

	@When("la fréquence est atteinte")
	public void whenLaFrequenceEstAtteinte() {
		minuterieFake.atteindreFrequence();
	}

	@When("la demande est ajoutée")
	public void whenLaDemandeEstAjoutee() {
	}

	@Then("la demande est assignée à la salle X")
	public void thenLaDemandeEstAssigneeALaSalleX() {
		assertTrue(demande.getSalleAssignee().equals(salle));
	}

	@Then("la fréquence est changée")
	public void thenLaFrequenceEstChangee() {
		verify(minuterie).setDelai(minute);
		verify(minuterie).reinitialiser();
	}

	@Then("le traitement des demandes est lancé")
	public void thenLeTraitementDesDemandesEstLance() {
		verify(assignateurMock).lancerAssignation();
	}

	@Then("la liste de demandes en attente contient la demande")
	public void thenLaListeDeDemandesEnAttenteContientLaDemande() {
		verify(conteneurDemandes).mettreDemandeEnAttente(demande);
	}

}
