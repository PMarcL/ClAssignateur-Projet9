package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class EntrepotSallesTest {
	private EntrepotSalles entrepotSalles;

	private static final Salle SALLE_QUELCONQUE_1 = new Salle(
			"SALLE_QUELCONQUE_1", 10);

	private static final Salle SALLE_QUELCONQUE_2 = new Salle(
			"SALLE_QUELCONQUE_2", 100);

	private static final Salle SALLE_QUELCONQUE_3 = new Salle(
			"SALLE_QUELCONQUE_3", 1000);

	private static final Salle SALLE_RESERVE = new Salle("SALLE_RESERVE", 10);

	private static final Calendar DATE_DEBUT = creerDate(2015, 07, 1, 12, 29, 0);
	private static final Calendar DATE_FIN = creerDate(2015, 07, 1, 12, 30, 0);
	private static final String ORGANISATEUR = "Simon";
	private static final int NOMBRE_PARTICIPANT = 10;

	private static final Demande DEMANDE_QUELCONQUE = new Demande(DATE_DEBUT,
			DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);

	public static Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void creerEntrepotSalles() {
		entrepotSalles = new EntrepotSalles();
	}

	@Test
	public void EntrepotSallesEstInitialementVide() {
		assertTrue(entrepotSalles.EstVide());
	}

	@Test
	public void lorsqueOnRangeUneSalleDansEntrepotIlNestPlusVide() {
		entrepotSalles.Ranger(SALLE_QUELCONQUE_1);

		assertFalse(entrepotSalles.EstVide());
	}

	@Test
	public void lorsqueUneSalleDisponibleEstDansLEntrepotOnPeutLObtenir() {
		try {
			entrepotSalles.Ranger(SALLE_QUELCONQUE_1);

			Salle salleObtenue = entrepotSalles
					.ObtenirSalleRepondantADemande(DEMANDE_QUELCONQUE);

			assertEquals(SALLE_QUELCONQUE_1, salleObtenue);
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver une salle disponible pour la demande.");
		}
	}

	@Test
	public void lorsquePlusieursSalleDisponibleSontDansLEntrepotOnPeutLesObtenirDansLOrdre() {
		try {
			entrepotSalles.Ranger(SALLE_QUELCONQUE_1);
			entrepotSalles.Ranger(SALLE_QUELCONQUE_2);
			entrepotSalles.Ranger(SALLE_QUELCONQUE_3);

			Salle salleObtenue1 = entrepotSalles
					.ObtenirSalleRepondantADemande(DEMANDE_QUELCONQUE);
			Salle salleObtenue2 = entrepotSalles
					.ObtenirSalleRepondantADemande(DEMANDE_QUELCONQUE);
			Salle salleObtenue3 = entrepotSalles
					.ObtenirSalleRepondantADemande(DEMANDE_QUELCONQUE);

			assertEquals(SALLE_QUELCONQUE_1, salleObtenue1);
			assertEquals(SALLE_QUELCONQUE_2, salleObtenue2);
			assertEquals(SALLE_QUELCONQUE_3, salleObtenue3);
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver les salles disponibles pour la demande.");
		}
	}

	@Test
	public void lorsqueOnObtientLaSalleUniqueDeLEntrepotIlDevientVide() {
		try {
			entrepotSalles.Ranger(SALLE_QUELCONQUE_1);

			entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_QUELCONQUE);

			assertTrue(entrepotSalles.EstVide());
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver une salle disponible pour la demande.");
		}
	}

	@Test(expected = AucunesSallesDisponiblesException.class)
	public void lorsqueSeulementUneSalleReserveEstDansLEntrepotIlYAUneExeption()
			throws AucunesSallesDisponiblesException {
		SALLE_RESERVE.placerReservation(DEMANDE_QUELCONQUE);
		entrepotSalles.Ranger(SALLE_RESERVE);

		entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_QUELCONQUE);
	}

	@Test(expected = AucunesSallesDisponiblesException.class)
	public void lorsqueAucunesSalleDisponibleEstDansLEntrepotIlYAUneExeption()
			throws AucunesSallesDisponiblesException {
		entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_QUELCONQUE);
	}

}
