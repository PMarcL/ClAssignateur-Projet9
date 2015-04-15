package org.ClAssignateur.services.reservations;

import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.AssignateurSalle;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.mockito.InOrder;
import org.junit.Before;
import org.junit.Test;

public class ServiceReservationSalleTest {

	private final Minute FREQUENCE_PAR_DEFAUT = new Minute(5);
	private final Minute FREQUENCE_MINUTES = new Minute(3);
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 10;
	private final int LIMITE_DEMANDES_QUELCONQUE = 5;
	private final String TITRE_DEMANDE_A_ANNULER = "DemandeAnnulee";

	private Minuterie minuterie;
	private AssignateurSalle assignateur;
	private Demande demande;

	private ServiceReservationSalle serviceReservation;

	@Before
	public void creerServiceReservation() {
		minuterie = mock(Minuterie.class);
		assignateur = mock(AssignateurSalle.class);
		demande = mock(Demande.class);

		serviceReservation = new ServiceReservationSalle(minuterie, assignateur);
	}

	@Test
	public void configureMinuteriePendantDemarrageService() {
		verify(minuterie).setDelai(FREQUENCE_PAR_DEFAUT);
	}

	@Test
	public void serviceSouscritNotificationMinuteriePendantDemarrageService() {
		verify(minuterie).souscrire(serviceReservation);
	}

	@Test
	public void demarreMinuteriePendantDemarrageService() {
		verify(minuterie).demarrer();
	}

	@Test
	public void quandSetFrequenceDevraitChangerFrequenceMinuterieAvantRedemarrerMinuterie() {
		serviceReservation.setFrequence(FREQUENCE_MINUTES);

		InOrder inOrder = inOrder(minuterie);
		inOrder.verify(minuterie).setDelai(FREQUENCE_MINUTES);
		inOrder.verify(minuterie).reinitialiser();
	}

	@Test
	public void quandAjouterDemandeDevraitAjouterDansAssignateur() {
		serviceReservation.ajouterDemande(demande);
		verify(assignateur).ajouterDemande(demande);
	}

	@Test
	public void etantDonneLimiteDemandeEnAttenteAtteinteQuandAjouterDemandeDevraitLancerAssignation() {
		given(assignateur.getNombreDemandesEnAttente()).willReturn(LIMITE_DEMANDES_PAR_DEFAUT);
		serviceReservation.ajouterDemande(demande);
		verify(assignateur).lancerAssignation();
	}

	@Test
	public void quandAnnulerDemandeDevraitAnnulerDansAssignateur() {
		serviceReservation.annulerDemande(TITRE_DEMANDE_A_ANNULER);
		verify(assignateur).annulerDemande(TITRE_DEMANDE_A_ANNULER);
	}

	@Test
	public void etantDonneNouvelleLimiteDemandeEnAttenteAtteinteQuandSetLimiteDemandesAvantAssignationDevraitDemanderAssignationDemandes() {
		given(assignateur.getNombreDemandesEnAttente()).willReturn(LIMITE_DEMANDES_QUELCONQUE);
		serviceReservation.setLimiteDemandesAvantAssignation(LIMITE_DEMANDES_QUELCONQUE);
		verify(assignateur).lancerAssignation();
	}

	@Test
	public void quandNotifierDelaiEcouleDevraitLancerAssignation() {
		serviceReservation.notifierDelaiEcoule();
		verify(assignateur).lancerAssignation();
	}

}
