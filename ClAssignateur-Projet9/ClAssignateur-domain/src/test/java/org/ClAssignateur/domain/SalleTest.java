package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.junit.Before;
import org.junit.Test;

public class SalleTest {

	private final Demande DEMANDE_AJOUTER = mock(Demande.class);
	private final int CAPACITE_INITIALE = 100;
	private final int NB_PARTICIPANT_INFERIEUR_A_CAPACITE = 50;
	private final int NB_PARTICIPANT_SUPERIEUR_A_CAPACITE = 150;

	private Salle salle;

	@Before
	public void initialisation() {
		willReturn(CAPACITE_INITIALE).given(DEMANDE_AJOUTER).getNbParticipant();
		salle = new Salle(CAPACITE_INITIALE);
	}

	@Test
	public void UneSallePeutAccueillirUnNombreDeParticipantInferieurASaCapacite() {
		assertTrue(salle.peutAccueillir(NB_PARTICIPANT_INFERIEUR_A_CAPACITE));
	}

	@Test
	public void UneSalleNePeutPasAccueillirUnNombreDeParticipantSuperieurASaCapacite() {
		assertFalse(salle.peutAccueillir(NB_PARTICIPANT_SUPERIEUR_A_CAPACITE));
	}

	@Test
	public void UneSalleContientInitialementAucuneReservation() {
		assertEquals(salle.getNbReservation(), 0);
	}

	@Test
	public void UneSalleApresReservationContientUneReservation() {
		salle.placerReservation(DEMANDE_AJOUTER);
		assertEquals(salle.getNbReservation(), 1);
	}

}
