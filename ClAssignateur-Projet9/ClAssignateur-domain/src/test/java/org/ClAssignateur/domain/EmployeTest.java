package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EmployeTest {

	private final String COURRIEL = "sim007@gmail.com";

	private Employe employe;

	@Before
	public void creerEmploye() {
		employe = new Employe(COURRIEL);
	}

	@Test
	public void employePossedeIntialementLeChampsCourrielCommeDefiniDansLeConstructeur() {
		String courriel = employe.getCourriel();
		assertEquals(COURRIEL, courriel);
	}

}
