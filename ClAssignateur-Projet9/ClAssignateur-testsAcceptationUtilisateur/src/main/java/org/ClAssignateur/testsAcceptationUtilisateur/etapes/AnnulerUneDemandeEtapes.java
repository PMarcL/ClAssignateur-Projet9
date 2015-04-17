package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleStrategie;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.groupe.Groupe;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import java.util.Optional;

public class AnnulerUneDemandeEtapes {

	private final String TITRE_DEMANDE_EN_ATTENTE = "Demande en attente";
	private final String TITRE_DEMANDE_ASSIGNEE = "Demande assignee";

	private ServiceReservationSalle service;
	private AssignateurSalle assignateur;
	private ConteneurDemandes conteneur;
	private Demande demandeEnAttente;
	private Optional<Demande> demandeEnAttenteOptional;
	private Optional<Demande> demandeAssigneeOptional;
	private Demande demandeAssignee;

	@BeforeScenario
	public void initialisationScenario() {
		conteneur = mock(ConteneurDemandes.class);

		assignateur = new AssignateurSalle(conteneur, mock(SallesEntrepot.class), mock(Notificateur.class),
				mock(SelectionSalleStrategie.class));
		service = new ServiceReservationSalle(mock(Minuterie.class), assignateur,
				mock(ReservationDemandeDTOAssembleur.class));

	}

	@Given("une demande assignée")
	public void givenUneDemandeAssignee() {
		demandeAssignee = new Demande(mock(Groupe.class), TITRE_DEMANDE_ASSIGNEE);
		demandeAssigneeOptional = Optional.of(demandeAssignee);
		given(conteneur.trouverDemandeSelonTitreReunion(TITRE_DEMANDE_ASSIGNEE)).willReturn(demandeAssigneeOptional);
	}

	@Given("une demande en attente de traitement")
	public void givenUneDemandeEnAttenteDeTraitement() {
		demandeEnAttente = new Demande(mock(Groupe.class), TITRE_DEMANDE_EN_ATTENTE);
		demandeEnAttenteOptional = Optional.of(demandeEnAttente);
		given(conteneur.trouverDemandeSelonTitreReunion(TITRE_DEMANDE_EN_ATTENTE)).willReturn(demandeEnAttenteOptional);
	}

	@When("on annule la demande assignée")
	public void whenOnAnnuleLaDemandeAssignee() {
		service.annulerDemande(TITRE_DEMANDE_ASSIGNEE);
	}

	@When("on annule la demande en attente")
	public void whenOnAnnuleLaDemandeEnAttente() {
		service.annulerDemande(TITRE_DEMANDE_EN_ATTENTE);
	}

	@Then("la demande assignée est annulée")
	public void thenLaDemandeAssigneeEstAnnulee() {
		assertTrue(demandeAssignee.getEtat().equals(Demande.StatutDemande.REFUSEE));
	}

	@Then("la demande en attente est annulée")
	public void thenLaDemandeEnAttenteEstAnnulee() {
		assertTrue(demandeEnAttente.getEtat().equals(Demande.StatutDemande.REFUSEE));
	}

	@Then("la demande en attente est archivée")
	public void thenLaDemandeEnAttenteEstArchivee() {
		verify(conteneur).archiverDemande(demandeEnAttente);
	}

	@Then("la demande assignée est archivée")
	public void thenLaDemandeAssigneeEstArchivee() {
		verify(conteneur).archiverDemande(demandeAssignee);
	}

	@Then("la salle assignée à cette demande lui est retirée")
	public void thenLaSalleAssigneeACetteDemandeLuiEstRetiree() {
		assertTrue(demandeAssignee.getSalleAssignee() == null);
	}

	@Then("le satut de la demande est changer pour un statut d'annulation")
	public void thenLeStatutDeLaDemandeEstChanger() {
		assertTrue(demandeAssignee.getEtat().equals(Demande.StatutDemande.REFUSEE));
	}
}
