package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class EntrepotSallesTest {
	private EntrepotSalles entrepotSalles;

	private Salle SALLE_CAPACITE_1;

	private Salle SALLE_CAPACITE_10;

	private Salle SALLE_CAPACITE_100;

	private Salle SALLE_CAPACITE_1000;

	private Salle SALLE_RESERVE;

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

		SALLE_CAPACITE_1 = mock(Salle.class);
		when(SALLE_CAPACITE_1.estDisponible(any(Demande.class))).thenReturn(true);
		when(SALLE_CAPACITE_1.getCapacite()).thenReturn(1);

		SALLE_CAPACITE_10 = mock(Salle.class);
		when(SALLE_CAPACITE_10.estDisponible(any(Demande.class))).thenReturn(true);
		when(SALLE_CAPACITE_10.getCapacite()).thenReturn(10);

		SALLE_CAPACITE_100 = mock(Salle.class);
		when(SALLE_CAPACITE_100.estDisponible(any(Demande.class))).thenReturn(true);
		when(SALLE_CAPACITE_100.getCapacite()).thenReturn(100);

		SALLE_CAPACITE_1000 = mock(Salle.class);
		when(SALLE_CAPACITE_1000.estDisponible(any(Demande.class))).thenReturn(true);
		when(SALLE_CAPACITE_1000.getCapacite()).thenReturn(1000);

		SALLE_RESERVE = mock(Salle.class);
		when(SALLE_RESERVE.estDisponible(any(Demande.class))).thenReturn(false);
		when(SALLE_RESERVE.getCapacite()).thenReturn(10);
	}

	@Test
	public void EntrepotSallesEstInitialementVide() {
		assertTrue(entrepotSalles.estVide());
	}

	@Test
	public void lorsqueOnRangeUneSalleDansEntrepotIlNestPlusVide() {
		entrepotSalles.ranger(SALLE_CAPACITE_10);

		assertFalse(entrepotSalles.estVide());
	}

	@Test
	public void lorsqueUneSalleDisponibleEstDansLEntrepotOnPeutLObtenir() {
		try {
			entrepotSalles.ranger(SALLE_CAPACITE_10);

			Salle salleObtenue = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);

			assertEquals(SALLE_CAPACITE_10, salleObtenue);
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver une salle disponible pour la demande.");
		}
	}

	@Test
	public void lorsquePlusieursSalleDisponibleSimilaireSontDansLEntrepotOnPeutLesObtenir() {
		try {
			entrepotSalles.ranger(SALLE_CAPACITE_10);
			entrepotSalles.ranger(SALLE_CAPACITE_10);
			entrepotSalles.ranger(SALLE_CAPACITE_10);

			Salle salleObtenue1 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
			Salle salleObtenue2 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
			Salle salleObtenue3 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);

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
			entrepotSalles.ranger(SALLE_CAPACITE_10);
			entrepotSalles.ranger(SALLE_CAPACITE_100);
			entrepotSalles.ranger(SALLE_CAPACITE_1000);

			Salle salleObtenue1 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
			Salle salleObtenue2 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_100_PARTICIPANTS);
			Salle salleObtenue3 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_1000_PARTICIPANTS);

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
			entrepotSalles.ranger(SALLE_CAPACITE_1);
			entrepotSalles.ranger(SALLE_CAPACITE_10);
			entrepotSalles.ranger(SALLE_CAPACITE_100);
			entrepotSalles.ranger(SALLE_CAPACITE_1000);

			Salle salleObtenue1 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_1_PARTICIPANTS);
			Salle salleObtenue2 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
			Salle salleObtenue3 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_100_PARTICIPANTS);
			Salle salleObtenue4 = entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_1000_PARTICIPANTS);

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
			entrepotSalles.ranger(SALLE_CAPACITE_10);

			entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);

			assertTrue(entrepotSalles.estVide());
		} catch (AucunesSallesDisponiblesException e) {
			fail("Le test dois avoir touver une salle disponible pour la demande.");
		}
	}

	@Test(expected = AucunesSallesDisponiblesException.class)
	public void lorsqueSeulementUneSalleReserveEstDansLEntrepotIlYAUneExeption()
			throws AucunesSallesDisponiblesException {
		entrepotSalles.ranger(SALLE_RESERVE);

		entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
	}

	@Test(expected = AucunesSallesDisponiblesException.class)
	public void lorsqueAucunesSalleDisponibleEstDansLEntrepotIlYAUneExeption() throws AucunesSallesDisponiblesException {
		entrepotSalles.obtenirSalleRepondantADemande(DEMANDE_10_PARTICIPANTS);
	}

}
