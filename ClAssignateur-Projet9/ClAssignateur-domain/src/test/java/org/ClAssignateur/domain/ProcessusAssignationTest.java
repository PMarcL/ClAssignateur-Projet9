package org.ClAssignateur.domain;

import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class ProcessusAssignationTest {

	private final int FREQUENCE_QUELCONQUE = 5;
	private final int LIMITE_QUELCONQUE = 10;
	private final Calendar DATE_DEBUT = creerDate(2015, 1, 31, 14, 55, 34);
	private final Calendar DATE_FIN = creerDate(2015, 1, 31, 15, 30, 49);
	private final int NOMBRE_PARTICIPANTS = 8;
	private final String NOM_ORGANISATEUR = "John Dow";

	private AssignateurSalle assignSalleMock;
	private ProcessusAssignation procAssignation;

	private static Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void etantDonneUnNouveauProcessusAssignation() {
		assignSalleMock = mock(AssignateurSalle.class);
		procAssignation = new ProcessusAssignation(assignSalleMock);
	}

	@Test
	public void quandSetFrequenceAppeleDevraitDelegerAAssignateurSalle() {
		procAssignation.setFrequence(FREQUENCE_QUELCONQUE);
		verify(assignSalleMock).setFrequence(FREQUENCE_QUELCONQUE);
	}

	@Test
	public void quandSetLimiteAppeleDevraitDeleguerAAssignateurSalle() {
		procAssignation.setLimite(LIMITE_QUELCONQUE);
		verify(assignSalleMock).setLimite(LIMITE_QUELCONQUE);
	}

	@Test
	public void quandAjouterDemandeAppeleDevraitDeleguerAAssignateurSalle() {
		procAssignation.ajouterDemande(DATE_DEBUT, DATE_FIN,
				NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
		verify(assignSalleMock).ajouterDemande(DATE_DEBUT, DATE_FIN,
				NOMBRE_PARTICIPANTS, NOM_ORGANISATEUR);
	}
}
