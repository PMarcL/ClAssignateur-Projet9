package org.ClAssignateur.domain;

import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class ServiceReservationSalleTest {

	private final int THREAD_TEST_TIMEOUT = 100;
	private final int FREQUENCE_QUELCONQUE = 3;
	private final int LIMITE_QUELCONQUE = 5;
	private final Calendar DATE_DEBUT = creerDate(2015, 1, 31, 14, 55, 34);
	private final Calendar DATE_FIN = creerDate(2015, 1, 31, 15, 30, 49);
	private final int NOMBRE_PARTICIPANTS = 8;
	private final String NOM_ORGANISATEUR = "John Dow";

	private AssignateurSalle assignSalleMock;
	private FileDemande fileDemandeMock;
	private EntrepotSalles entrepotSallesMock;

	private ServiceReservationSalle serviceReservation;

	private static Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	private void demarrerServiceReservation() {
		serviceReservation.demarrer();
	}

	private void arreterServiceReservation() {
		serviceReservation.arreter();
	}

	@Before
	public void etantDonneUnNouveauServiceReservationSalle() {
		assignSalleMock = mock(AssignateurSalle.class);
		fileDemandeMock = mock(FileDemande.class);
		entrepotSallesMock = mock(EntrepotSalles.class);

		serviceReservation = new ServiceReservationSalle(assignSalleMock,
				fileDemandeMock, entrepotSallesMock);
	}

	@Test
	public void quandSetFrequenceDevraitEtreModifieeDansAssignateurSalle() {
		serviceReservation.setFrequence(FREQUENCE_QUELCONQUE);
		verify(assignSalleMock).setFrequence(FREQUENCE_QUELCONQUE);
	}

	@Test
	public void quandSetLimiteDevraitEtreModifieeDansAssignateurSalle() {
		serviceReservation.setLimite(LIMITE_QUELCONQUE);
		verify(assignSalleMock).setLimite(LIMITE_QUELCONQUE);
	}

	@Test
	public void quandAjouteDemandeDevraitEtreDelegueAFileDemande() {
		serviceReservation.ajouterDemande(DATE_DEBUT, DATE_FIN,
				NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
		verify(fileDemandeMock).ajouter(DATE_DEBUT, DATE_FIN,
				NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
	}

	@Test(timeout = THREAD_TEST_TIMEOUT)
	public void quandDemarreServiceDevraitDemanderAssignationDesSalles() {
		demarrerServiceReservation();
		verify(assignSalleMock, atLeastOnce()).assignerDemandeSalle(
				fileDemandeMock, entrepotSallesMock);
		arreterServiceReservation();
	}

	@Test(timeout = THREAD_TEST_TIMEOUT)
	public void quandArretServiceDevraitNePlusDemanderAssignationDesSalles() {
		demarrerServiceReservation();
		arreterServiceReservation();
		verify(assignSalleMock, never()).assignerDemandeSalle(fileDemandeMock,
				entrepotSallesMock);
	}

}
