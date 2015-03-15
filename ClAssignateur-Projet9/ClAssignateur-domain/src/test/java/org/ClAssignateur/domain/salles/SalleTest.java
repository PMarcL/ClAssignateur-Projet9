package org.ClAssignateur.domain.salles;

import static org.junit.Assert.*;

import org.ClAssignateur.domain.salles.Salle;
import org.junit.Before;
import org.junit.Test;

public class SalleTest {

	private final String NOM_SALLE = "salle";
	private final int CAPACITE_INITIALE = 100;
	private final int NB_PARTICIPANTS_INFERIEUR_A_CAPACITE = 50;
	private final int NB_PARTICIPANTS_SUPERIEUR_A_CAPACITE = 150;
	private final float TAUX_OCCUPATION_POUR_50_PARTICIPANTS = 0.5f;

	private Salle salle;

	@Before
	public void initialisation() {
		salle = new Salle(CAPACITE_INITIALE, NOM_SALLE);
	}

	@Test
	public void uneSalleRetourneSonNom() {
		String nom = salle.getNom();
		assertTrue(nom.equals(NOM_SALLE));
	}

	@Test
	public void uneSallePeutAccueillirUnNombreDeParticipantInferieurASaCapacite() {
		assertTrue(salle.peutAccueillir(NB_PARTICIPANTS_INFERIEUR_A_CAPACITE));
	}

	@Test
	public void uneSalleNePeutPasAccueillirUnNombreDeParticipantSuperieurASaCapacite() {
		assertFalse(salle.peutAccueillir(NB_PARTICIPANTS_SUPERIEUR_A_CAPACITE));
	}

	@Test
	public void uneSalleCalculeSonPourcentageOccupationSelonUnNombreDeParticipants() {
		float resultat = salle.getTauxOccupation(NB_PARTICIPANTS_INFERIEUR_A_CAPACITE);
		assertEquals(TAUX_OCCUPATION_POUR_50_PARTICIPANTS, resultat, 0.01);
	}

}
