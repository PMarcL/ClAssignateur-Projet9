package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

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
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import java.util.ArrayList;

public class MaximiserLesPlacesDansSalleEtapes {

	private final int PLACES_50 = 50;
	private final int PLACES_100 = 100;
	private final String NOM_SALLE_50_PARTICIPANTS = "PLT2050";
	private final String NOM_SALLE_100_PARTICIPANTS = "PLT2100";
	private final String NOM_SALLE_100_PARTICIPANTS_SECONDE = "PLT2102";
	private final Priorite PRIORITE = Priorite.moyenne();
	private Demande demandeAAssigner;
	private EnMemoireDemandeEntrepot demandesTraitees;
	private SallesEntrepot salles;
	private ConteneurDemandes conteneurDemandes;
	private AssignateurSalle assignateur;

	public MaximiserLesPlacesDansSalleEtapes() {
		demandesTraitees = new EnMemoireDemandeEntrepot();
		conteneurDemandes = new ConteneurDemandes(new EnMemoireDemandeEntrepot(), demandesTraitees);
		salles = new EnMemoireSallesEntrepot();

		assignateur = new AssignateurSalle(conteneurDemandes, salles, mock(Notificateur.class),
				new SelectionSalleOptimaleStrategie());

		demandeAAssigner = creerDemandeAvecDeuxParticipant();
	}

	@Given("plusieurs salle disponible")
	public void givenPlusieursSallesDisponible() {
		salles.persister(new Salle(PLACES_100, NOM_SALLE_100_PARTICIPANTS));
		salles.persister(new Salle(PLACES_50, NOM_SALLE_50_PARTICIPANTS));
	}

	@Given("deux salles optimales avec meme nombre de place")
	public void givenDeuxSallesOptimalesAvecMemeNombreDePlace() {
		salles.persister(new Salle(PLACES_100, NOM_SALLE_100_PARTICIPANTS));
		salles.persister(new Salle(PLACES_100, NOM_SALLE_100_PARTICIPANTS_SECONDE));
	}

	@When("assigner salle")
	public void whenAssignerSalle() {
		conteneurDemandes.mettreDemandeEnAttente(demandeAAssigner);
		assignateur.lancerAssignation();
	}

	@Then("la salle assigne est celle avec le minimum de place pour la reunion")
	public void thenLaSalleAssigneEstCelleAvecLeMinimumDePlacePourLaReunion() {
		Salle salleAssignee = demandeAAssigner.getSalleAssignee();
		assertEquals(NOM_SALLE_50_PARTICIPANTS, salleAssignee.getNom());
	}

	@Then("une des deux salle est assignee")
	public void thenUneDesDeuxSalleEstAssignee() {
		assertTrue(demandeAAssigner.estAssignee());
	}

	private Demande creerDemandeAvecDeuxParticipant() {
		InformationsContact organisateur = new InformationsContact("uncourriel@gmail.com");
		InformationsContact responsable = new InformationsContact("uncourriel2@gmail.com");

		ArrayList<InformationsContact> participants = new ArrayList<InformationsContact>();
		participants.add(organisateur);
		participants.add(responsable);

		ContactsReunion groupe = new ContactsReunion(organisateur, responsable, participants);
		return new Demande(groupe, "Demande avec deux participant", PRIORITE);
	}
}
