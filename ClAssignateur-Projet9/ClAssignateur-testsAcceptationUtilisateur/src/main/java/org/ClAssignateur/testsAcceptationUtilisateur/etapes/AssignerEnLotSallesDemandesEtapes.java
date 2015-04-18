package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AssignerEnLotSallesDemandesEtapes {
	private final int X_NOMBRE_DEMANDES = 10;
	private final int Y_NOMBRE_DEMANDES = 1;

	private ServiceReservationSalle serviceReservation;
	private AssignateurSalle assignateurSalle;
	private Minuterie minuterie;
	private ReservationDemandeDTOAssembleur assembleur;
	private ReservationDemandeDTO dto;
	private Demande demande;

	@BeforeScenario
	public void initialisationScenario() {
		minuterie = mock(Minuterie.class);
		assignateurSalle = mock(AssignateurSalle.class);
		demande = mock(Demande.class);
		assembleur = mock(ReservationDemandeDTOAssembleur.class);

		given(assembleur.assemblerDemande(dto)).willReturn(demande);
		serviceReservation = new ServiceReservationSalle(minuterie, this.assignateurSalle, assembleur);
	}

	@Given("une limite de X demandes")
	public void givenUneLimiteDeXDemandes() {
		serviceReservation.setLimiteDemandesAvantAssignation(X_NOMBRE_DEMANDES);
	}

	@When("la limite de X demandes est atteinte")
	public void whenLaLimiteDeXDemandesEstAtteinte() {
		given(assignateurSalle.getNombreDemandesEnAttente()).willReturn(X_NOMBRE_DEMANDES);
		serviceReservation.ajouterDemande(dto);
	}

	@When("je configure le système pour tolérer Y demandes")
	public void whenJeConfigureLeSystemePourTolererYDemandes() {
		given(assignateurSalle.getNombreDemandesEnAttente()).willReturn(Y_NOMBRE_DEMANDES);
		serviceReservation.setLimiteDemandesAvantAssignation(Y_NOMBRE_DEMANDES);
	}

	@Then("l'assignation des demandes en attente est déclenchée")
	public void thenLassignationDesDemandesEnAttenteEstDeclenchee() {
		verify(this.assignateurSalle).lancerAssignation();
	}

	@Then("la minuterie est réinitialisée")
	public void thenLaMinuterieEstReinitialisee() {
		verify(minuterie).reinitialiser();
	}

	@Then("la limite est modifiée")
	public void thenLaLimiteEstModifiee() {
		verify(this.assignateurSalle).lancerAssignation();
	}
}
