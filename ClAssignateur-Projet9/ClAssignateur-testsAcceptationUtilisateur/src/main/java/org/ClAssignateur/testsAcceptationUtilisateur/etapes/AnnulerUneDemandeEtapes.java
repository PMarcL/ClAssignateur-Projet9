package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.junit.Assert.*;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.DeclencheurAssignateurSalle;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.ConteneurDemandesFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fixtures.DemandeConstructeur;
import org.ClAssignateur.testsAcceptationUtilisateur.fixtures.SalleConstructeur;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AnnulerUneDemandeEtapes {

	private final String TITRE_DEMANDE_EN_ATTENTE = "Demande en attente";
	private final String TITRE_DEMANDE_ASSIGNEE = "Demande assignee";

	private Demande demandeAssignee;
	private Demande demandeEnAttente;

	@Given("une demande assignée")
	public void givenUneDemandeAssignee() {
		this.demandeAssignee = new DemandeConstructeur().titre(TITRE_DEMANDE_ASSIGNEE).construireDemande();
		Salle salle = new SalleConstructeur().construireSalle();
		assignerDemandeA(salle);
	}

	@Given("une demande en attente de traitement")
	public void givenUneDemandeEnAttenteDeTraitement() {
		this.demandeEnAttente = new DemandeConstructeur().titre(TITRE_DEMANDE_EN_ATTENTE).construireDemande();
		mettreDemandeEnAttente();
	}

	@When("on annule la demande assignée")
	public void whenOnAnnuleLaDemandeAssignee() {
		new ServiceReservationSalle().annulerDemande(TITRE_DEMANDE_ASSIGNEE);
	}

	@When("on annule la demande en attente")
	public void whenOnAnnuleLaDemandeEnAttente() {
		new ServiceReservationSalle().annulerDemande(TITRE_DEMANDE_EN_ATTENTE);
	}

	@Then("la demande assignée est annulée")
	public void thenLaDemandeAssigneeEstAnnulee() {
		assertTrue(demandeEstAnnulee(this.demandeAssignee));
	}

	@Then("la demande en attente est annulée")
	public void thenLaDemandeEnAttenteEstAnnulee() {
		assertTrue(demandeEstAnnulee(this.demandeEnAttente));
	}

	@Then("la demande en attente est archivée")
	public void thenLaDemandeEnAttenteEstArchivee() {
		ConteneurDemandesFake conteneur = obtenirConteneurDemandes();
		assertTrue(conteneur.demandesEstArchivee(this.demandeEnAttente));
	}

	@Then("la demande assignée est archivée")
	public void thenLaDemandeAssigneeEstArchivee() {
		ConteneurDemandesFake conteneur = obtenirConteneurDemandes();
		assertTrue(conteneur.demandesEstArchivee(this.demandeAssignee));
	}

	@Then("la salle assignée à cette demande lui est retirée")
	public void thenLaSalleAssigneeACetteDemandeLuiEstRetiree() {
		assertTrue(demandeAssignee.getSalleAssignee() == null);
	}

	@Then("le satut de la demande est changer pour un statut d'annulation")
	public void thenLeStatutDeLaDemandeEstChanger() {
		assertTrue(demandeEstAnnulee(this.demandeAssignee));
	}

	private void assignerDemandeA(Salle salleAssignee) {
		this.demandeAssignee.placerReservation(salleAssignee);
		obtenirConteneurDemandes().archiverDemande(this.demandeAssignee);
	}

	private ConteneurDemandesFake obtenirConteneurDemandes() {
		return (ConteneurDemandesFake) LocalisateurServices.getInstance().obtenir(ConteneurDemandes.class);
	}

	private boolean demandeEstAnnulee(Demande demande) {
		return demande.getEtat().equals(Demande.StatutDemande.ANNULEE);
	}

	private void mettreDemandeEnAttente() {
		LocalisateurServices.getInstance().obtenir(DeclencheurAssignateurSalle.class)
				.ajouterDemande(this.demandeEnAttente);
	}
}
