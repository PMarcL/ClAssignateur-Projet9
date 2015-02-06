package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class EntrepotSallesTest {
	private EntrepotSalles entrepotSalles;

	private static final Salle SALLE_CAPACITE_1 = new Salle("SALLE_CAPACITE_1", 1);

	private static final Salle SALLE_CAPACITE_10 = new Salle("SALLE_CAPACITE_10", 10);

	private static final Salle SALLE_CAPACITE_100 = new Salle("SALLE_CAPACITE_100", 100);

	private static final Salle SALLE_CAPACITE_1000 = new Salle("SALLE_CAPACITE_1000", 1000);

	private static final Salle SALLE_RESERVE = new Salle("SALLE_RESERVE", 10);

	private static final Calendar DATE_DEBUT = creerDate(2015, 07, 1, 12, 29, 0);
	private static final Calendar DATE_FIN = creerDate(2015, 07, 1, 12, 30, 0);
	private static final String ORGANISATEUR = "Simon";

	private static final Demande DEMANDE_1_PARTICIPANTS = new Demande(DATE_DEBUT, DATE_FIN, 1, ORGANISATEUR);
	private static final Demande DEMANDE_10_PARTICIPANTS = new Demande(DATE_DEBUT, DATE_FIN, 10, ORGANISATEUR);
	private static final Demande DEMANDE_100_PARTICIPANTS = new Demande(DATE_DEBUT, DATE_FIN, 100, ORGANISATEUR);
	private static final Demande DEMANDE_1000_PARTICIPANTS = new Demande(DATE_DEBUT, DATE_FIN, 1000, ORGANISATEUR);

	public static Calendar creerDate(int annee, int mois, int jour, int heure, int minute, int seconde) {
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
		entrepotSalles.Ranger(SALLE_CAPACITE_10);

		assertFalse(entrepotSalles.EstVide());
	}

	@Test
	public void lorsqueUneSalleDisponibleEstDansLEntrepotOnPeutLObtenir() {
		try {
			entrepotSalles.Ranger(SALLE_CAPACITE_10);

			Salle salleObtenue = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);

			assertEquals(SALLE_CAPACITE_10, salleObtenue);
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver une salle disponible pour la demande.");
		}
	}

	@Test
	public void lorsquePlusieursSalleDisponibleSimilaireSontDansLEntrepotOnPeutLesObtenir() {
		try {
			entrepotSalles.Ranger(SALLE_CAPACITE_10);
			entrepotSalles.Ranger(SALLE_CAPACITE_10);
			entrepotSalles.Ranger(SALLE_CAPACITE_10);

			Salle salleObtenue1 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
			Salle salleObtenue2 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
			Salle salleObtenue3 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);

			assertEquals(SALLE_CAPACITE_10, salleObtenue1);
			assertEquals(SALLE_CAPACITE_10, salleObtenue2);
			assertEquals(SALLE_CAPACITE_10, salleObtenue3);
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver les salles disponibles pour la demande.");
		}
	}

	@Test
	public void lorsqueNbImpaireDeSalleDisponiblesSontDansLEntrepotOnPeutLesObtenir() {
		try {
			entrepotSalles.Ranger(SALLE_CAPACITE_10);
			entrepotSalles.Ranger(SALLE_CAPACITE_100);
			entrepotSalles.Ranger(SALLE_CAPACITE_1000);

			Salle salleObtenue1 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
			Salle salleObtenue2 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_100_PARTICIPANTS);
			Salle salleObtenue3 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_1000_PARTICIPANTS);

			assertEquals(SALLE_CAPACITE_10, salleObtenue1);
			assertEquals(SALLE_CAPACITE_100, salleObtenue2);
			assertEquals(SALLE_CAPACITE_1000, salleObtenue3);
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver les salles disponibles pour la demande.");
		}
	}

	@Test
	public void lorsqueNbPaireDeSalleDisponiblesSontDansLEntrepotOnPeutLesObtenir() {
		try {
			entrepotSalles.Ranger(SALLE_CAPACITE_1);
			entrepotSalles.Ranger(SALLE_CAPACITE_10);
			entrepotSalles.Ranger(SALLE_CAPACITE_100);
			entrepotSalles.Ranger(SALLE_CAPACITE_1000);

			Salle salleObtenue1 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_1_PARTICIPANTS);
			Salle salleObtenue2 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
			Salle salleObtenue3 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_100_PARTICIPANTS);
			Salle salleObtenue4 = entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_1000_PARTICIPANTS);

			assertEquals(SALLE_CAPACITE_1, salleObtenue1);
			assertEquals(SALLE_CAPACITE_10, salleObtenue2);
			assertEquals(SALLE_CAPACITE_100, salleObtenue3);
			assertEquals(SALLE_CAPACITE_1000, salleObtenue4);
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver les salles disponibles pour la demande.");
		}
	}

	@Test
	public void lorsqueOnObtientLaSalleUniqueDeLEntrepotIlDevientVide() {
		try {
			entrepotSalles.Ranger(SALLE_CAPACITE_10);

			entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);

			assertTrue(entrepotSalles.EstVide());
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver une salle disponible pour la demande.");
		}
	}

	@Test(expected = AucunesSallesDisponiblesException.class)
	public void lorsqueSeulementUneSalleReserveEstDansLEntrepotIlYAUneExeption()
			throws AucunesSallesDisponiblesException {
		SALLE_RESERVE.placerReservation(DEMANDE_10_PARTICIPANTS);
		entrepotSalles.Ranger(SALLE_RESERVE);

		entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
	}

	@Test(expected = AucunesSallesDisponiblesException.class)
	public void lorsqueAucunesSalleDisponibleEstDansLEntrepotIlYAUneExeption() throws AucunesSallesDisponiblesException {
		entrepotSalles.ObtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
	}

}
