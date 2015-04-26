package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import org.ClAssignateur.services.reservations.DeclencheurAssignateurSalleTest;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AssignerEnLotSallesDemandesEtapes {

	@Given("une limite de X demandes")
	public void givenUneLimiteDeXDemandes() {
		// inutile car le code est contenu dans un test unitaire qui est appelé
		// dans la clause Then
	}

	@When("la limite de X demandes est atteinte")
	public void whenLaLimiteDeXDemandesEstAtteinte() {
		// inutile car le code est contenu dans un test unitaire qui est appelé
		// dans la clause Then
	}

	@When("je configure le système pour tolérer Y demandes")
	public void whenJeConfigureLeSystemePourTolererYDemandes() {
		// inutile car le code est contenu dans un test unitaire qui est appelé
		// dans la clause Then
	}

	@Then("l'assignation des demandes en attente est déclenchée")
	public void thenLassignationDesDemandesEnAttenteEstDeclenchee() {
		DeclencheurAssignateurSalleTest declencheurTest = initialiserTestUnitaire();
		declencheurTest.etantDonneLimiteDemandeEnAttenteAtteinteQuandAjouterDemandeDevraitLancerAssignation();
	}

	@Then("la minuterie est réinitialisée")
	public void thenLaMinuterieEstReinitialisee() {
		DeclencheurAssignateurSalleTest declencheurTest = initialiserTestUnitaire();
		declencheurTest.etantDonneLimiteDemandeEnAttenteAtteinteQuandAjouterDeamndeDevraitReinitiliserMinuterie();
	}

	@Then("la limite est modifiée")
	public void thenLaLimiteEstModifiee() {
		DeclencheurAssignateurSalleTest declencheurTest = initialiserTestUnitaire();
		declencheurTest
				.etantDonneNouvelleLimiteDemandeEnAttenteAtteinteQuandSetLimiteDemandesAvantAssignationDevraitDemanderAssignationDemandes();
	}

	private DeclencheurAssignateurSalleTest initialiserTestUnitaire() {
		DeclencheurAssignateurSalleTest declencheurTest = new DeclencheurAssignateurSalleTest();
		declencheurTest.initialisation();
		return declencheurTest;
	}
}
