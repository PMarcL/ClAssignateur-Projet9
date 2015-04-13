package org.ClAssignateur.services;

import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.AssignateurSalle;
import org.ClAssignateur.services.ServiceReservationSalle;
import org.mockito.InOrder;
import org.junit.Before;
import org.junit.Test;

public class ServiceReservationSalleTest {

	private final int MILLISECONDES_PAR_MINUTE = 60000;
	private final int FREQUENCE_PAR_DEFAUT = 5;
	private final int FREQUENCE_MINUTES = 3;
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 10;
	private final int LIMITE_DEMANDES_QUELCONQUE = 5;
	private final String TITRE_DEMANDE_A_ANNULER = "DemandeAnnulee";

	private MinuterieStrategie minuterie;
	private AssignateurSalle assignateur;
	private Demande demande;

	private ServiceReservationSalle serviceReservation;

	@Before
	public void creerServiceReservation() {
		minuterie = mock(MinuterieStrategie.class);
		assignateur = mock(AssignateurSalle.class);
		demande = mock(Demande.class);

		serviceReservation = new ServiceReservationSalle(minuterie, assignateur);
	}

	@Test
	public void demarreMinuteriePendantDemarrageService() {
		long delaiMillisecondes = delaiEnMillisecondes(FREQUENCE_PAR_DEFAUT);
		verify(minuterie).planifierAppelPeriodique(assignateur, delaiMillisecondes, delaiMillisecondes);
	}

	@Test
	public void quandSetFrequenceDevraitRedemarrerMinuterie() {
		serviceReservation.setFrequence(FREQUENCE_MINUTES);

		long delaiMillisecondes = delaiEnMillisecondes(FREQUENCE_MINUTES);
		InOrder inOrder = inOrder(minuterie);
		inOrder.verify(minuterie).annulerAppelPeriodique();
		inOrder.verify(minuterie).planifierAppelPeriodique(assignateur, delaiMillisecondes, delaiMillisecondes);
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

	private long delaiEnMillisecondes(int delaiEnMinutes) {
		return delaiEnMinutes * MILLISECONDES_PAR_MINUTE;
	}

}
