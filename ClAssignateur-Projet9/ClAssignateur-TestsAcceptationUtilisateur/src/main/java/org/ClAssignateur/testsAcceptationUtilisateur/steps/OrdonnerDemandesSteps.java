package org.ClAssignateur.testsAcceptationUtilisateur.steps;

import static org.junit.Assert.*;

import org.ClAssignateur.domain.AssignateurSalle;
import org.ClAssignateur.domain.SelectionSalleOptimaleStrategie;
import org.ClAssignateur.domain.demandes.ConteneurDemandes;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.Priorite;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.groupe.Groupe;
import org.ClAssignateur.domain.notification.Notificateur;
import org.ClAssignateur.domain.notification.NotificationEnConsole;
import org.ClAssignateur.domain.salles.EnMemoireSallesEntrepot;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.salles.SallesEntrepot;
import org.ClAssignateur.persistences.EnMemoireDemandeEntrepot;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import java.util.ArrayList;

public class OrdonnerDemandesSteps {

	private final Groupe GROUPE = new Groupe(new Employe("organisateur@hotmail.com"), new Employe(
			"responsable@hotmail.com"), new ArrayList<Employe>());
	private final String TITRE_DEMANDE_PRIORITE_BASSE = "Demande basse priorite";
	private final String TITRE_DEMANDE_PRIORITE_HAUTE = "Demande haute priorite";

	private EnMemoireDemandeEntrepot demandesTraitees;
	private SallesEntrepot salles;
	private ConteneurDemandes conteneurDemandes;
	private AssignateurSalle assignateur;

	public OrdonnerDemandesSteps() {
		demandesTraitees = new EnMemoireDemandeEntrepot();
		conteneurDemandes = new ConteneurDemandes(new EnMemoireDemandeEntrepot(), demandesTraitees);
		salles = new EnMemoireSallesEntrepot();
		salles.persister(new Salle(100, "PLT2770"));
		assignateur = new AssignateurSalle(conteneurDemandes, salles, new Notificateur(new NotificationEnConsole()),
				new SelectionSalleOptimaleStrategie());
	}

	@Given("une demande à priorité basse en attente")
	public void givenUneDemandeAPrioriteBasse() {
		Demande demandeBassePriorite = new Demande(GROUPE, TITRE_DEMANDE_PRIORITE_BASSE, Priorite.basse());
		assignateur.ajouterDemande(demandeBassePriorite);
	}

	@Given("une demande à priorité haute en attente")
	public void givenUneDemandeAPrioriteHaute() {
		Demande demandeHautePriorite = new Demande(GROUPE, TITRE_DEMANDE_PRIORITE_HAUTE, Priorite.haute());
		assignateur.ajouterDemande(demandeHautePriorite);
	}

	@Given("plusieurs demandes de même priorité en attente de traitement")
	public void givenPlusieursDemandesDeMemePriorite() {

	}

	@When("les demandes sont traitées")
	public void whenLesDemandesSontTraitees() {
		assignateur.run();
	}

	@Then("la demande à priorité haute est traitée avant celle à priorité basse")
	public void thenLaDemandeAPrioriteHauteEstTraiteeAvantCelleAPrioriteBasse() {
		Demande derniereDemandeTraitee = demandesTraitees.getDerniereDemandePersistee();
		assertTrue(TITRE_DEMANDE_PRIORITE_BASSE.equals(derniereDemandeTraitee.getTitre()));
	}

	@Then("les demandes sont traitées selon leur ordre d'arrivée")
	public void thenLesDemandesSontTraiteesSelonLeurOrdreArrivee() {

	}

}
