package org.ClAssignateur.domaine.groupe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EmployeTest {
	final String ADRESSE_COURRIEL_VALIDE = "monadresse@domaine.com";
	final String AUTRE_ADRESSE_COURRIEL_VALIDE = "masecondeadresse@autredomaine.com";

	private Employe employe;

	@Before
	public void initialisation() {
		employe = new Employe(ADRESSE_COURRIEL_VALIDE);
	}

	@Test
	public void etantDonneUnEmployeAvecUneAdresseCourrielValideQuandGetAdresseCourrielDevraitRetournerAdresseCourriel() {
		assertEquals(ADRESSE_COURRIEL_VALIDE, employe.getAdresseCourriel());
	}

	@Test(expected = AdresseCourrielInvalideException.class)
	public void etantDonneUnEmployeAvecUneAdresseCourrielInvalideQuandCreationDevraitLancerException() {
		final String ADRESSE_COURRIEL_INVALIDE = "CeciEstUneAdresseCourrielInvalide";
		new Employe(ADRESSE_COURRIEL_INVALIDE);
	}

	@Test
	public void etantDonneDeuxEmployesAvecAdressesCourrielIdentiquesQuandEqualsDevraitRetournerVrai() {
		Employe autreEmploye = new Employe(ADRESSE_COURRIEL_VALIDE);
		assertTrue(employe.equals(autreEmploye));
	}

	@Test
	public void etantDonneDeuxEmployesAvecDeuxAdressesCourrielsDifferentesQuandEqualsDevraitRetournerFaux() {
		Employe autreEmploye = new Employe(AUTRE_ADRESSE_COURRIEL_VALIDE);
		assertFalse(employe.equals(autreEmploye));
	}

}
