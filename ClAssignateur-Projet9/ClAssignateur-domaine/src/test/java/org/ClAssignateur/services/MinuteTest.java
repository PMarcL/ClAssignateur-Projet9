package org.ClAssignateur.services;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

public class MinuteTest {

	private final int VALEUR_MINUTE_1 = 1;
	private final int VALEUR_MINUTE_2 = 2;

	private Minute minute1;

	@Before
	public void initialisation() {
		minute1 = new Minute(VALEUR_MINUTE_1);
	}

	@Test
	public void etanDonneDeuxInstanceAvecMemeValeurQuandEqualsDevraitRetournerVrai() {
		Minute autreMinute1 = new Minute(VALEUR_MINUTE_1);
		assertTrue(minute1.equals(autreMinute1));
	}

	@Test
	public void etantDonneDeuxInstanceAvecValeursDifferentesQuandEqualsDevraitRetournerFaux() {
		Minute minute2 = new Minute(VALEUR_MINUTE_2);
		assertFalse(minute1.equals(minute2));
	}

	@Test
	public void etantDonneObjetTypeDifferentDeMinuteQuandEqualsDevraitRetournerFaux() {
		String objTypeDifferent = "a";
		assertFalse(minute1.equals(objTypeDifferent));
	}

	@Test
	public void quandGetDelaiEnMillisecondesDevraitConvertirDelaiMinuteEnMillisecondes() {
		final int NB_MILLISECONDES_1_MINUTE = 60000;
		assertEquals(NB_MILLISECONDES_1_MINUTE, minute1.getDelaiMillisecondes());
	}
}
