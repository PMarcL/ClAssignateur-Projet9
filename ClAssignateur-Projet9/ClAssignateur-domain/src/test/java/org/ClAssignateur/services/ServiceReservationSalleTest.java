package org.ClAssignateur.services;

import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.AssignateurSalle;
import org.ClAssignateur.services.ServiceReservationSalle;
import org.mockito.InOrder;
import java.util.Timer;
import org.junit.Before;
import org.junit.Test;

public class ServiceReservationSalleTest {

	private final int MILLISECONDES_PAR_MINUTE = 60000;
	private final int FREQUENCE_PAR_DEFAUT = 5;
	private final int FREQUENCE_MINUTES = 3;
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 10;
	private final int LIMITE_DEMANDES_QUELCONQUE = 5;

	private Timer minuterie;
	private AssignateurSalle assignateur;
	private Demande demande;

	private ServiceReservationSalle serviceReservation;

	@Before
	public void creerServiceReservation() {
		minuterie = mock(Timer.class);
		assignateur = mock(AssignateurSalle.class);
		demande = mock(Demande.class);

		serviceReservation = new ServiceReservationSalle(minuterie, assignateur);
	}

	@Test
	public void demarreMinuteriePendantDemarrageService() {
		long delaiMillisecondes = delaiEnMillisecondes(FREQUENCE_PAR_DEFAUT);
		verify(minuterie).scheduleAtFixedRate(assignateur, delaiMillisecondes, delaiMillisecondes);
	}

	@Test
	public void quandSetFrequenceDevraitRedemarrerMinuterie() {
		serviceReservation.setFrequence(FREQUENCE_MINUTES);

		long delaiMillisecondes = delaiEnMillisecondes(FREQUENCE_MINUTES);
		InOrder inOrder = inOrder(minuterie);
		inOrder.verify(minuterie).cancel();
		inOrder.verify(minuterie).scheduleAtFixedRate(assignateur, delaiMillisecondes, delaiMillisecondes);
	}

	@Test
	public void quandAjouterDemandeDevraitAjouterDansAssignateur() {
		serviceReservation.ajouterDemande(demande);
		verify(assignateur).ajouterDemande(demande);
	}

	@Test
	public void quandAjouterDemandeDevraitDemanderAssignationDemandes() {
		serviceReservation.ajouterDemande(demande);
		verify(assignateur).assignerDemandeSalleSiContientAuMoins(LIMITE_DEMANDES_PAR_DEFAUT);
	}

	@Test
	public void quandAnnulerDemandeDevraitAnnulerDansAssignateur() {
		serviceReservation.annulerDemande(demande);
		verify(assignateur).annulerDemande(demande);
	}

	@Test
	public void quandSetLimiteDemandesAvantAssignationDevraitDemanderAssignationDemandes() {
		serviceReservation.setLimiteDemandesAvantAssignation(LIMITE_DEMANDES_QUELCONQUE);
		verify(assignateur).assignerDemandeSalleSiContientAuMoins(LIMITE_DEMANDES_QUELCONQUE);
	}

	private long delaiEnMillisecondes(int delaiEnMinutes) {
		return delaiEnMinutes * MILLISECONDES_PAR_MINUTE;
	}

}
