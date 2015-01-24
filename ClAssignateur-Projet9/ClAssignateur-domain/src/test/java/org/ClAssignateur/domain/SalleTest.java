package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;
import org.ClAssignateur.domain.Salle;

public class SalleTest {

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
		// assertTrue(salle.estDisponible(demandeAVerifier));
	}

	@Test
	public void UneNouvelleSalleApresReservationEstPlusDisponibleALaDateDemande() {
		// TODO
	}

	@Test
	public void SalleAvecUneReservationApresRetraitDeLaReservationEstDisponible() {
		// TODO
	}

}
