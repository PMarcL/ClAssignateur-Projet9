package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleOptimaleStrategie;
import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.persistance.EnMemoireDemandeEntrepot;
import org.ClAssignateur.persistance.EnMemoireSallesEntrepot;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.EnMemoireDemandesEntrepotFake;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrdonnerDemandesEtapes {

	private final int NB_PARTICIPANTS = 5;
	private final String TITRE_DEMANDE = "Réunion de 15 minutes";
	private final int NB_DEMANDES_DE_MEME_PRIORITE = 5;
	private final UUID ID_DEMANDE_FAIBLE_PRIORITE = UUID.randomUUID();
	private final UUID ID_DEMANDE_HAUTE_PRIORITE = UUID.randomUUID();

	private ContactsReunion contactsReunion;
	private EnMemoireDemandesEntrepotFake demandesTraitees;
	private SallesEntrepot salles;
	private ConteneurDemandes conteneurDemandes;
	private AssignateurSalle assignateur;
	private Demande demandePrioriteBasse;
	private Demande demandePrioriteHaute;

	@BeforeScenario
	public void initialisation() {
		contactsReunion = mock(ContactsReunion.class);
		demandesTraitees = new EnMemoireDemandesEntrepotFake();
		conteneurDemandes = new ConteneurDemandes(new EnMemoireDemandeEntrepot(), demandesTraitees);
		salles = new EnMemoireSallesEntrepot();
		salles.persister(new Salle(100, "PLT2770"));

		demandePrioriteBasse = new Demande(ID_DEMANDE_FAIBLE_PRIORITE, NB_PARTICIPANTS, contactsReunion, TITRE_DEMANDE,
				Priorite.basse());
		demandePrioriteHaute = new Demande(ID_DEMANDE_HAUTE_PRIORITE, NB_PARTICIPANTS, contactsReunion, TITRE_DEMANDE,
				Priorite.haute());
		assignateur = new AssignateurSalle(conteneurDemandes, salles, mock(Notificateur.class),
				new SelectionSalleOptimaleStrategie());
	}

	@Given("une demande à priorité basse en attente")
	public void givenUneDemandeAPrioriteBasse() {
		assignateur.ajouterDemande(demandePrioriteBasse);
	}

	@Given("une demande à priorité haute en attente")
	public void givenUneDemandeAPrioriteHaute() {
		assignateur.ajouterDemande(demandePrioriteHaute);
	}

	@Given("plusieurs demandes de même priorité en attente de traitement")
	public void givenPlusieursDemandesDeMemePriorite() {
		for (int i = 0; i < NB_DEMANDES_DE_MEME_PRIORITE; i++) {
			Demande demandePrioriteBasse = new Demande(NB_PARTICIPANTS, contactsReunion, TITRE_DEMANDE,
					Priorite.basse());
			assignateur.ajouterDemande(demandePrioriteBasse);
		}
	}

	@When("les demandes sont traitées")
	public void whenLesDemandesSontTraitees() {
		assignateur.lancerAssignation();
	}

	@Then("la demande à priorité haute est traitée avant celle à priorité basse")
	public void thenLaDemandeAPrioriteHauteEstTraiteeAvantCelleAPrioriteBasse() {
		Demande derniereDemandeTraitee = demandesTraitees.getDerniereDemandePersistee();
		Optional<Demande> demandeHautePriorite = demandesTraitees.obtenirDemandeSelonId(ID_DEMANDE_HAUTE_PRIORITE);

		assertTrue(demandeHautePriorite.isPresent());
		assertEquals(ID_DEMANDE_FAIBLE_PRIORITE, derniereDemandeTraitee.getID());
	}

	@Then("les demandes sont traitées selon leur ordre d'arrivée")
	public void thenLesDemandesSontTraiteesSelonLeurOrdreArrivee() {
		List<Demande> demandes = demandesTraitees.obtenirDemandes();

		for (int i = 0; i < NB_DEMANDES_DE_MEME_PRIORITE - 1; i++) {
			Demande demandeCourante = demandes.get(i);
			Demande demandeSuivante = demandes.get(i + 1);
			assertTrue(demandeCourante.estAnterieureA(demandeSuivante));
		}
	}

}
