package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.mockito.BDDMockito.*;

import java.util.Timer;

import org.ClAssignateur.domain.AssignateurSalle;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.services.ServiceReservationSalle;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AssignerEnLotSallesDemandesEtapes {
	private final int MAXIMUM_DEMANDE_TOLEREES = 10;

	private ServiceReservationSalle serviceReservation;
	private AssignateurSalle assignateurSalle;

	@BeforeScenario
	public void initialisationScenario() {
		Timer minuterie = mock(Timer.class);
		assignateurSalle = mock(AssignateurSalle.class);
		serviceReservation = new ServiceReservationSalle(minuterie, this.assignateurSalle);
	}

	@Given("j'ai configuré le système pour tolérer X demandes")
	public void givenJaiConfigueéLeSystemePourTolerer10Demandes() {
		serviceReservation.setLimiteDemandesAvantAssignation(MAXIMUM_DEMANDE_TOLEREES);
	}

	@When("le nombre de demandes en attente atteint X demandes")
	public void whenLeNombreDeDemandesEnAttenteAtteint10Demandes() {
		Demande demande = mock(Demande.class);
		given(assignateurSalle.getNombreDemandesEnAttente()).willReturn(MAXIMUM_DEMANDE_TOLEREES);
		serviceReservation.ajouterDemande(demande);
	}

	@Then("l'assignation des demandes en attente est déclenchée")
	public void thenLassignationDesDemandesEnAttenteEstDeclenchee() {
		verify(this.assignateurSalle).lancerAssignation();
	}
}
