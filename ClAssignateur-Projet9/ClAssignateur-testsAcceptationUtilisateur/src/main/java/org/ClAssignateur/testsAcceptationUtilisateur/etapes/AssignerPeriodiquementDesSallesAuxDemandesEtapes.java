package org.ClAssignateur.testsAcceptationUtilisateur.etapes;

import static org.junit.Assert.*;
import java.util.List;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.DeclencheurAssignateurSalle;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.ConteneurDemandesFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.MinuterieFake;
import org.ClAssignateur.testsAcceptationUtilisateur.fixtures.DemandeConstructeur;
import org.ClAssignateur.testsAcceptationUtilisateur.fixtures.SalleConstructeur;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AssignerPeriodiquementDesSallesAuxDemandesEtapes {
	private final int TROIS_MINUTES = 3;

	private Salle salleX;
	private Demande demande;
	private Demande demandeX;
	private Demande demandeY;
	private Minute frequence3Minutes;

	@Given("une salle disponible X")
	public void givenUneSalleDisponibleX() {
		this.salleX = new SalleConstructeur().construireSalle();
		persisterSalle();
	}

	@Given("une demande en attente")
	public void givenUneDemandeEnAttente() {
		this.demande = new DemandeConstructeur().construireDemande();
		mettreDemandeEnAttente(this.demande);
	}

	@Given("une demande X en attente")
	public void givenUneDemandeXDePrioriteMoyenneEnAttente() {
		this.demandeX = creerDemandePrioriteMoyenne();
		mettreDemandeEnAttente(this.demandeX);
	}

	@Given("une fréquence de 3 minutes")
	public void givenUneFrequenceX() {
		this.frequence3Minutes = new Minute(TROIS_MINUTES);
	}

	@Given("une demande Y en attente")
	public void givenUneDemandeYDePrioriteMoyenneEnAttente() {
		this.demandeY = creerDemandePrioriteMoyenne();
		mettreDemandeEnAttente(this.demandeY);
	}

	@Given("une fréquence quelconque")
	public void givenUneFrequenceQuelconque() {
		givenUneFrequenceX();
	}

	@Given("une nouvelle demande")
	public void givenUneNouvelleDemande() {
		this.demande = new DemandeConstructeur().construireDemande();
	}

	@When("les demandes en attente sont traitées")
	public void whenLesDemandesEnAttenteSontTraitees() {
		LocalisateurServices.getInstance().obtenir(DeclencheurAssignateurSalle.class).notifierDelaiEcoule();
	}

	@When("je change la fréquence de traitement")
	public void whenJeChangeLaFrequenceDeTraitementPourLaFrequenceX() {
		new ServiceReservationSalle().setFrequence(this.frequence3Minutes);
	}

	@When("la fréquence de traitement est atteinte")
	public void whenLaFrequenceDeTraitementEstAtteinte() {
		MinuterieFake minuterie = (MinuterieFake) LocalisateurServices.getInstance().obtenir(Minuterie.class);
		minuterie.atteindreFrequence();
	}

	@When("la demande est mise en attente")
	public void whenLaDemandeEstMiseEnAttente() {
		mettreDemandeEnAttente(this.demande);
	}

	@Then("la demande est assignée à la salle X")
	public void thenLaDemandeEstAssigneeALaSalleX() {
		Salle salleAssignee = this.demande.getSalleAssignee();
		assertTrue(salleAssignee.equals(this.salleX));
	}

	@Then("la demande X est traitée avant la demande Y")
	public void thenLaDemandeXEstTraiteeAvantLaDemandeY() {
		Demande premiereDemandeTraitee = retirerDemandeTraitee();
		Demande deuxiemeDemandeTraitee = retirerDemandeTraitee();

		assertEquals(this.demandeX, premiereDemandeTraitee);
		assertEquals(this.demandeY, deuxiemeDemandeTraitee);
	}

	@Then("les demandes en attente sont traitées aux 3 minutes")
	public void thenLesDemandesEnAttenteSontTraiteesAux3Minutes() {
		Minute delaiMinuterie = obtenirDelaiMinuterie();
		assertEquals(TROIS_MINUTES, delaiMinuterie.valeur);
	}

	@Then("la demande en attente est traitée")
	public void thenLaDemandeEnAttenteEstTraitée() {
		ConteneurDemandesFake conteneurDemandes = (ConteneurDemandesFake) LocalisateurServices.getInstance().obtenir(
				ConteneurDemandes.class);
		assertTrue(conteneurDemandes.demandesEstArchivee(this.demande));
	}

	@Then("la liste de demandes en attente contient la demande")
	public void thenLaListeDeDemandesEnAttenteContientLaDemande() {
		ConteneurDemandes conteneurDemandes = LocalisateurServices.getInstance().obtenir(ConteneurDemandes.class);
		List<Demande> demandesEnAttente = conteneurDemandes.obtenirDemandesEnAttenteOrdrePrioritaire();
		assertTrue(demandesEnAttente.contains(this.demande));
	}

	private Minute obtenirDelaiMinuterie() {
		MinuterieFake minuterie = (MinuterieFake) LocalisateurServices.getInstance().obtenir(Minuterie.class);
		return minuterie.getDelai();
	}

	private Demande retirerDemandeTraitee() {
		ConteneurDemandesFake conteneurDemandes = (ConteneurDemandesFake) LocalisateurServices.getInstance().obtenir(
				ConteneurDemandes.class);
		return conteneurDemandes.retirerDemandeTraitee();
	}

	private void mettreDemandeEnAttente(Demande demande) {
		LocalisateurServices.getInstance().obtenir(DeclencheurAssignateurSalle.class).ajouterDemande(demande);
	}

	private void persisterSalle() {
		LocalisateurServices.getInstance().obtenir(SallesEntrepot.class).persister(this.salleX);
	}

	private Demande creerDemandePrioriteMoyenne() {
		return new DemandeConstructeur().priorite(Priorite.moyenne()).construireDemande();
	}
}
