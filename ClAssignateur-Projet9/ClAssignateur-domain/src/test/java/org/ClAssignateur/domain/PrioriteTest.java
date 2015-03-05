package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

public class PrioriteTest {

	Priorite prioriteBasse;
	Priorite prioriteMoyenne;
	Priorite prioriteHaute;

	@Before
	public void creerPriorites() {
		prioriteBasse = Priorite.basse();
		prioriteMoyenne = Priorite.moyenne();
		prioriteHaute = Priorite.haute();
	}

	@Test
	public void prioriteBasseEstLaMoinsPrioritaire() {
		assertFalse(prioriteBasse.estPlusPrioritaire(prioriteMoyenne));
		assertFalse(prioriteBasse.estPlusPrioritaire(prioriteHaute));
	}

	@Test
	public void prioriteMoyenneEstPlusPrioritaireQuePrioriteBasse() {
		assertTrue(prioriteMoyenne.estPlusPrioritaire(prioriteBasse));
	}

	@Test
	public void prioriteMoyenneEstMoinsPrioritaireQuePrioriteHaute() {
		assertFalse(prioriteMoyenne.estPlusPrioritaire(prioriteHaute));
	}

	@Test
	public void prioriteHauteEstLaPlusPrioritaire() {
		assertTrue(prioriteHaute.estPlusPrioritaire(prioriteBasse));
		assertTrue(prioriteHaute.estPlusPrioritaire(prioriteMoyenne));
	}

	@Test
	public void deuxPrioriteEquivalentesNeSontPasPlusPrioritaire() {
		assertFalse(prioriteBasse.estPlusPrioritaire(prioriteBasse));
		assertFalse(prioriteMoyenne.estPlusPrioritaire(prioriteMoyenne));
		assertFalse(prioriteHaute.estPlusPrioritaire(prioriteHaute));
	}
}
