package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;

import org.junit.Test;

public class ReservationTest {

	private Demande demande;
	private Salle salle;
	private Reservation reservation;

	@Before
	public void creerReservation() {
		demande = mock(Demande.class);
		salle = mock(Salle.class);
		reservation = new Reservation(demande, salle);
	}

	@Test
	public void quandCreeDevraitContenirDemandeEnvoyee() {
		Demande resultat = reservation.getDemande();
		assertEquals(demande, resultat);
	}

	@Test
	public void quandCreeDevraitContenirSalleEnvoyee() {
		Salle resultat = reservation.getSalle();
		assertEquals(salle, resultat);
	}

	/*
	 * @Test public void
	 * etantDonneeDemandeContenueQuandConcerneDemandeDevraitRepondreVrai() {
	 * boolean resultat = reservation.concerneDemande(demande);
	 * assertTrue(resultat); }
	 */

	@Test
	public void etantDonneDemandeNonConcerneeQuandConcerneDemandeDevraitRepondreFaux() {
		Demande demandeNonConcernee = mock(Demande.class);
		boolean resultat = reservation.concerneDemande(demandeNonConcernee);
		assertFalse(resultat);
	}
}
