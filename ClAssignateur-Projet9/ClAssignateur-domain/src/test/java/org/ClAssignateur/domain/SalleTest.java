package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.Salle;
import org.ClAssignateur.domain.Demande;

public class SalleTest {

	private final Demande DEMANDE_TEST = new Demande(new Date(1985, 4, 12, 10,
			45, 0), new Date(1985, 4, 12, 11, 30, 0), 100, new Organisateur(
			"P-M"));
	private final Demande DEMANDE_TEST2 = new Demande(new Date(1985, 4, 13, 10,
			45, 0), new Date(1985, 4, 13, 11, 30, 0), 100, new Organisateur(
			"P-M"));
	private Salle salle;
	private final String NOM_TEST = "nomSalle";
	private final int CAPACITE_TEST = 100;

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
