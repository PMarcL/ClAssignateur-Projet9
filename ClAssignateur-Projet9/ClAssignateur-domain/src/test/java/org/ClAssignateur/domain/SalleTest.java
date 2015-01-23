package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SalleTest {

	private final int UNE_CAPACITE_QUELCONQUE = 12;
	private final String UN_LOCAL_QUELCONQUE = "PLT-2551";

	private Salle salleTestee;

	@Before
	public void initialement() {
		salleTestee = new Salle(UN_LOCAL_QUELCONQUE, UNE_CAPACITE_QUELCONQUE);
	}

	@Test
	public void uneNouvelleSalleConnaitSonLocal() {
		assertEquals(UN_LOCAL_QUELCONQUE, salleTestee.GetLocal());
	}

	@Test
	public void uneNouvelleSalleConnaitSaCapacite() {
		assertEquals(UNE_CAPACITE_QUELCONQUE, salleTestee.GetCapacite());
	}

}
