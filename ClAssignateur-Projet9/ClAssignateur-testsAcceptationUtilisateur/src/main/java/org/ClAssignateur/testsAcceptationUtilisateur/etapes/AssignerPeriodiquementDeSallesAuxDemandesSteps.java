package org.ClAssignateur.testsAcceptationUtilisateur.etapes;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleOptimaleStrategie;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.groupe.Employe;
import org.ClAssignateur.domaine.groupe.Groupe;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.EnMemoireDemandeEntrepot;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.EnMemoireSallesEntrepot;
import org.ClAssignateur.testsAcceptationUtilisateur.fakes.NotificationSilencieuse;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class AssignerPeriodiquementDeSallesAuxDemandesSteps    {

	private final int NB_DEMANDES = 5;
	private final String TITRE_DEMANDE = "Réunion de 15 minutes";
	
	private AssignateurSalle assignateurSalle;
	private ConteneurDemandes conteneurDemandes;
	private Demande demande;
	private Employe organisateur;
	private Employe responsable;
	private EnMemoireDemandeEntrepot demandesTraitees;
	private Groupe groupe;
	private Minuterie minuterie;
	private ReservationDemandeDTOAssembleur assembleur;
	private SallesEntrepot salles;

	private ServiceReservationSalle serviceReservation;
	
	@BeforeScenario
	public void initialisation() {
		
		organisateur = new Employe("organisateur@gmail.com");
		responsable =  new Employe("responsable@gmail.com");
		groupe = new Groupe(organisateur, responsable, new ArrayList<Employe>());
		demandesTraitees = new EnMemoireDemandeEntrepot();
		conteneurDemandes = new ConteneurDemandes(new EnMemoireDemandeEntrepot(), demandesTraitees);
		salles = new EnMemoireSallesEntrepot();
		
		assignateurSalle = new AssignateurSalle(conteneurDemandes, salles, new Notificateur(new NotificationSilencieuse()),
				new SelectionSalleOptimaleStrategie());
	}
	 
    @Given("une salle")
    public void givenUneSalle() {
    	Salle salle = new Salle(100, "PLT2770");
		salles.persister(salle);
    }
    
    @Given("une demande")
    public void givenUneDemande() {
    	demande = new Demande(groupe, TITRE_DEMANDE);   
    	conteneurDemandes.mettreDemandeEnAttente(demande);
    }
    
    @Given("plusieurs demandes en attente de traitement")
	public void givenPlusieursDemandesEnAttenteDeTraitement() {
    	creerDesDemandes();
	}
   
    @Given("une frécence par défault")
    public void givenUneFrecenParDefault() {
    	minuterie = mock(Minuterie.class);
    	assembleur = mock(ReservationDemandeDTOAssembleur.class);
    	serviceReservation = new ServiceReservationSalle (minuterie, assignateurSalle, assembleur);
    }
    
    @Given("des demandes sont ajoutées séquentiellement")
    public void givenUneNouvelleDemandeEstAjouteToutesLes30Secondes() {
    	creerDesDemandes();
    }

    @When("la demande est traité")
    public void whenUneDemandeEstCreer() {
    	assignateurSalle.lancerAssignation();
    }
    
   @When("les demandes sont tous traitées")
    public void whenLesDemandesSontTousTraitees() {
	   assignateurSalle.lancerAssignation();
    }
   @When("la fréquence est modifiée")
    public void whenLaFrequenceEstMofidiee() {
	    Minute nbMinutes = new Minute(5);
    	serviceReservation.setFrequence(nbMinutes);
    }
    
    @When("la frécence est atteinte")
    public void whenLaFrequenceEstAtteinte() {
    	assignateurSalle.lancerAssignation();
    }
  
    @Then("la demande est assignée à la première salle disponible")
    public void thenLaDemandeEstAssigneeALaPremiereSalleDisponible() {	
    	assertTrue(demande.estAssignee());
    }
    
    @Then("les demandes ont été traitées dans leur l'ordre d'arrivée")
    public void thenLesDemandesOntEteTraiteesDansLeurOrdreDArrivee() {	
    	
    	List<Demande> listeDemandes = demandesTraitees.obtenirDemandes();
    	for (int i = 0; i < NB_DEMANDES - 1; i++) {
			Demande demandeCourante = listeDemandes.get(i);
			Demande demandeSuivante = listeDemandes.get(i + 1);
			assertTrue(demandeCourante.estAnterieureA(demandeSuivante));
		}
    }
    
    @Then("la fréquence est changée")
    public void thenLaFrequenceEstChangee() {	
    	verify(minuterie).reinitialiser();
    }
    
    @Then("les nouvelles demandes sont traitées")
    public void thenLesNouvellesDemandesSontTraitées() {
    	assertEquals(0, conteneurDemandes.getNombreDemandesEnAttente());
  
    }
    
    private void creerDesDemandes(){
    	for (int i = 0; i < NB_DEMANDES; i++) {
			Demande demandeEnAttente = new Demande(groupe, TITRE_DEMANDE, Priorite.basse());
			assignateurSalle.ajouterDemande(demandeEnAttente);
		}
    }
    
}
