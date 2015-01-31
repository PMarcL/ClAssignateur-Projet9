package org.ClAssignateur.domain;

import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class ServiceReservationSalleTest {

	private final int FREQUENCE_QUELCONQUE = 3;
	private final int LIMITE_QUELCONQUE = 5;
	private final Calendar DATE_DEBUT = creerDate(2015, 1, 31, 14, 55, 34);
	private final Calendar DATE_FIN = creerDate(2015, 1, 31, 15, 30, 49);
	private final int NOMBRE_PARTICIPANTS = 8;
	private final String NOM_ORGANISATEUR = "John Dow";

	private ProcessusAssignation procAssignationMock;
	private ServiceReservationSalle serviceReservation;

	private static Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void etantDonneUnNouveauServiceReservationSalle() {
		procAssignationMock = mock(ProcessusAssignation.class);
		serviceReservation = new ServiceReservationSalle(procAssignationMock);
	}

	@Test
	public void quandSetFrequenceAlorsDevraitEtreModifieeDansProcessusAssignation() {
		serviceReservation.setFrequence(FREQUENCE_QUELCONQUE);
		verify(procAssignationMock).setFrequence(FREQUENCE_QUELCONQUE);
	}

	@Test
	public void quandSetLimiteAlorsDevraitEtreModifieeDansAssignateurSalle() {
		serviceReservation.setLimite(LIMITE_QUELCONQUE);
		verify(procAssignationMock).setLimite(LIMITE_QUELCONQUE);
	}

	@Test
	public void quandAjouteDemandeAlorsDevraitEtreAjouteeDansAssignateurSalle() {
		serviceReservation.ajouterDemande(DATE_DEBUT, DATE_FIN,
				NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
		verify(procAssignationMock).ajouterDemande(DATE_DEBUT, DATE_FIN,
				NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
	}

	@Test
	public void quandDemarreDevraitDemarrerProcessusAssignation() {
		serviceReservation.demarrer();
		verify(procAssignationMock).demarrer();
	}

	@Test
	public void quandArreteDevraitArreterProcessusAssignation() {
		serviceReservation.arreter();
		verify(procAssignationMock).arreter();
	}

}
