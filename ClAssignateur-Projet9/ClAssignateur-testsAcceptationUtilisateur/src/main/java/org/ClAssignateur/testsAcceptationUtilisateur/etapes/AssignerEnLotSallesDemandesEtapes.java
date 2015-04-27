package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.junit.Assert.*;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.DeclencheurAssignateurSalle;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.ConteneurDemandesFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.MinuterieFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fixtures.DemandeConstructeur;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AssignerEnLotSallesDemandesEtapes {
	private final int LIMITE_2_DEMANDES = 2;
	private final int LIMITE_1_DEMANDE = 1;

	private Demande demande1;
	private Demande demande2;

	@Given("une limite de 2 demandes")
	public void givenUneLimiteDeXDemandes() {
		configurerLimiteDemandesEnAttente(LIMITE_2_DEMANDES);
	}

	@When("j'ajoute une demande en attente")
	public void whenJAjouteUneDemandeEnAttente() {
		this.demande1 = construireDemande();
		ajouterDemande(this.demande1);
	}

	@When("la limite de demandes en attente est atteinte")
	public void whenLaLimiteDeXDemandesEstAtteinte() {
		this.demande1 = construireDemande();
		ajouterDemande(this.demande1);

		this.demande2 = construireDemande();
		ajouterDemande(this.demande2);
	}

	@When("je configure le système pour tolérer 1 demandes")
	public void whenJeConfigureLeSystemePourTolererYDemandes() {
		configurerLimiteDemandesEnAttente(LIMITE_1_DEMANDE);
	}

	@Then("l'assignation des demandes en attente est déclenchée")
	public void thenLassignationDesDemandesEnAttenteEstDeclenchee() {
		assertTrue(demandeEstTraitee(this.demande1));
		assertTrue(demandeEstTraitee(this.demande2));
	}

	@Then("la minuterie est réinitialisée")
	public void thenLaMinuterieEstReinitialisee() {
		MinuterieFake minuterie = (MinuterieFake) LocalisateurServices.getInstance().obtenir(Minuterie.class);
		assertTrue(minuterie.aEteReinitialisee());
	}

	@Then("le traitement des demandes est lancé")
	public void thenLeTraitementDesDemandesEstLance() {
		assertTrue(demandeEstTraitee(this.demande1));
	}

	private void configurerLimiteDemandesEnAttente(int limite) {
		obtenirDeclencheur().setLimiteDemandesAvantAssignation(limite);
	}

	private DeclencheurAssignateurSalle obtenirDeclencheur() {
		return LocalisateurServices.getInstance().obtenir(DeclencheurAssignateurSalle.class);
	}

	private Demande construireDemande() {
		return new DemandeConstructeur().construireDemande();
	}

	private void ajouterDemande(Demande demande) {
		obtenirDeclencheur().ajouterDemande(demande);
	}

	private boolean demandeEstTraitee(Demande demande) {
		ConteneurDemandesFake conteneurDemandes = (ConteneurDemandesFake) LocalisateurServices.getInstance().obtenir(
				ConteneurDemandes.class);
		return conteneurDemandes.demandesEstArchivee(demande);
	}
}
