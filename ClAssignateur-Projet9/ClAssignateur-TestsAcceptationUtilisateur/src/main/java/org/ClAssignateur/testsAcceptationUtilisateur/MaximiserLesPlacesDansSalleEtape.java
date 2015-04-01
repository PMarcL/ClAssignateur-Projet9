package org.ClAssignateur.testsAcceptationUtilisateur;

import org.ClAssignateur.domain.salles.SelectionSalleOptimaleStrategieTest;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class MaximiserLesPlacesDansSalleEtape {

	@Given("plusieurs salle disponible")
	public void givenPlusieursSallesDisponible() {

	}

	@Given("deux salles optimales avec meme nombre de place")
	public void whenDeuxSallesOptimalesAvecMemeNombreDePlace() {

	}

	@When("assigner salle")
	public void whenAssignerSalle() {

	}

	@Then("la salle assigne est celle avec le minimum de place pour la reunion")
	public void thenLaSalleAssigneEstCelleAvecLeMinimumDePlacePourLaReunion() {
		SelectionSalleOptimaleStrategieTest selectionSalleOptimaleStrategieTest = new SelectionSalleOptimaleStrategieTest();
		selectionSalleOptimaleStrategieTest
				.etantDonnePlusieursSallesPouvantAccueillirNbParticipantsQuandRechercheSalleRetourneSalleOptimale();
	}

	@Then("une des deux salle est assignee")
	public void thenUneDesDeuxSalleEstAssignee() {
		SelectionSalleOptimaleStrategieTest selectionSalleOptimaleStrategieTest = new SelectionSalleOptimaleStrategieTest();
		selectionSalleOptimaleStrategieTest.etantDonneDeuxSallesOptimalesRenvoieLaPremiereTrouvee();
	}

}
