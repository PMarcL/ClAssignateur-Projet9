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
	private final int Y_NOMBRE_DEMANDES = 5;

	private ServiceReservationSalle serviceReservation;
	private AssignateurSalle assignateurSalle;
	private Minuterie minuterie;
	private ReservationDemandeDTOAssembleur assembleur;
	private ReservationDemandeDTO dto;

	@BeforeScenario
	public void initialisationScenario() {
		minuterie = mock(Minuterie.class);
		assignateurSalle = mock(AssignateurSalle.class);
		assembleur = mock(ReservationDemandeDTOAssembleur.class);

		serviceReservation = new ServiceReservationSalle(minuterie, this.assignateurSalle, assembleur);
	}

	@Given("j'ai configuré le système pour tolérer X demandes")
	public void givenJaiConfigueéLeSystemePourTolerer10Demandes() {
		serviceReservation.setLimiteDemandesAvantAssignation(X_NOMBRE_DEMANDES);
	}

	@Given("il y a présentement Y demandes en attente")
	public void givenIlYAPresentementYDemandesEnAttente() {
		given(assignateurSalle.getNombreDemandesEnAttente()).willReturn(Y_NOMBRE_DEMANDES);
	}

	@When("le nombre de demandes en attente atteint X demandes")
	public void whenLeNombreDeDemandesEnAttenteAtteint10Demandes() {
		Demande demande = mock(Demande.class);
		given(assignateurSalle.getNombreDemandesEnAttente()).willReturn(X_NOMBRE_DEMANDES);
		given(assembleur.assemblerDemande(dto)).willReturn(demande);
		serviceReservation.ajouterDemande(dto);
	}

	@When("je configure le système pour tolérer Y demandes")
	public void whenJeConfigureLeSystemePourTolererYDemandes() {
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
}
