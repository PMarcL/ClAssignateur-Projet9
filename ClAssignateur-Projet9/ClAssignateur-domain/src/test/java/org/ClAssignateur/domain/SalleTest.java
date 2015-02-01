package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.Salle;
import org.ClAssignateur.domain.Demande;

public class SalleTest {

	private final Demande DEMANDE_TEST = new Demande(creerDate(2015, 01, 28,
			11, 0, 0), creerDate(2015, 01, 28, 12, 0, 0), 100, "P-M");
	private final Demande DEMANDE_TEST2 = new Demande(creerDate(2015, 02, 28,
			11, 0, 0), creerDate(2015, 02, 28, 12, 0, 0), 100, "P-M");
	private final String NOM_TEST = "nomSalle";
	private final int CAPACITE_TEST = 100;

	private Salle salle;

	public static Calendar creerDate(int annee, int mois, int jour, int heure,
			int minute, int seconde) {
		Calendar date = Calendar.getInstance();
		date.set(annee, mois, jour, heure, minute, seconde);
		return date;
	}

	@Before
	public void initialisation() {
		salle = new Salle(NOM_TEST, CAPACITE_TEST);
	}

	@Test
	public void UneNouvelleSalleRetourneSonNom() {
		String nomRecu = salle.getNom();

		assertEquals(NOM_TEST, nomRecu);
	}

	@Test
	public void UneNouvelleSalleRetourneSaCapacite() {
		int capaciteRecu = salle.getCapacite();

		assertEquals(CAPACITE_TEST, capaciteRecu);
	}

	@Test
	public void UneNouvelleSalleSansReservationEstDisponible() {
		assertTrue(salle.estDisponible(DEMANDE_TEST));
	}

	@Test
	public void UneNouvelleSalleApresReservationNEstPlusDisponibleALaDateDemande() {
		salle.placerReservation(DEMANDE_TEST);
		assertFalse(salle.estDisponible(DEMANDE_TEST));
	}

	@Test
	public void SalleAvecUneReservationEstDisponibleAUneAutreDate() {
		salle.placerReservation(DEMANDE_TEST);
		assertTrue(salle.estDisponible(DEMANDE_TEST2));
	}

	@Test
	public void SalleAvecUneReservationApresRetraitDeLaReservationEstDisponible() {
		salle.placerReservation(DEMANDE_TEST);
		salle.enleverReservation(DEMANDE_TEST);
		assertTrue(salle.estDisponible(DEMANDE_TEST));
	}

}
