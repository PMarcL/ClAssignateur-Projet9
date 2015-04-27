package org.ClAssignateur.TestDeFlot;

import static org.junit.Assert.*;

import org.ClAssignateur.contexte.developpement.ContexteDeveloppement;
import org.ClAssignateur.domaine.demandes.Demande.EtatDemande;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.localisateur.LocalisateurServices;
import org.ClAssignateur.services.reservations.DeclencheurAssignateurSalle;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.UUID;

public class FlotNormalTest {

	private int NB_DE_PARTICIPANTS = 50;
	private String COURRIEL_ORGANISATEUR = "organisateur@hotmail.com";
	private int PRIORITE = 5;
	private ArrayList<String> LISTE_PARTICIPANTS = new ArrayList<>();

	private ReservationDemandeDTO demandeReservationDTO;
	private InformationsDemandeDTO demandeInformationDTO;
	private ServiceReservationSalle serviceReservation;
	private ServiceInformationsDemande serviceInformation;
	private DeclencheurAssignateurSalle declencheur;
	private UUID demandeID;

	@BeforeClass
	public static void initialisationDuContexte() {
		new ContexteDeveloppement().appliquer();
	}

	@Before
	public void initialisation() {
		serviceInformation = new ServiceInformationsDemande();
		serviceReservation = new ServiceReservationSalle();
		demandeReservationDTO = new ReservationDemandeDTO();
		initialiserDemandeDTO();

		demandeID = serviceReservation.ajouterDemande(demandeReservationDTO);
	}

	@Test
	public void verifierStatutDemandeEnAttente() {
		demandeInformationDTO = serviceInformation.getInformationsDemande(COURRIEL_ORGANISATEUR, demandeID);
		assertEquals(EtatDemande.EN_ATTENTE, demandeInformationDTO.statutDemande);
		assertNull(demandeInformationDTO.salleAssigne);
	}

	@Test
	public void verifierStatutDemandeAssignee() {
		lancerAssignation();
		demandeInformationDTO = serviceInformation.getInformationsDemande(COURRIEL_ORGANISATEUR, demandeID);
		assertEquals(EtatDemande.ACCEPTEE, demandeInformationDTO.statutDemande);
		assertNotNull(demandeInformationDTO.salleAssigne);
	}

	@After
	public void annulerReservation() {
		serviceReservation.annulerDemande(demandeID);
		demandeInformationDTO = serviceInformation.getInformationsDemande(COURRIEL_ORGANISATEUR, demandeID);
		assertEquals(EtatDemande.ANNULEE, demandeInformationDTO.statutDemande);
	}

	private void initialiserDemandeDTO() {
		demandeReservationDTO.courrielOrganisateur = COURRIEL_ORGANISATEUR;
		demandeReservationDTO.nombreParticipants = NB_DE_PARTICIPANTS;
		demandeReservationDTO.priorite = PRIORITE;
		demandeReservationDTO.participantsCourriels = LISTE_PARTICIPANTS;
	}

	private void lancerAssignation() {
		declencheur = LocalisateurServices.getInstance().obtenir(DeclencheurAssignateurSalle.class);
		declencheur.notifierDelaiEcoule();
	}
}
