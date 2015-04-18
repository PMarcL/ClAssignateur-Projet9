package org.ClAssignateur.domaine.contacts;

import static org.junit.Assert.*;

import org.ClAssignateur.domaine.contacts.AdresseCourrielInvalideException;
import org.ClAssignateur.domaine.contacts.InformationsContact;

import org.junit.Before;
import org.junit.Test;

public class InformationsContactTest {
	final String ADRESSE_COURRIEL_VALIDE = "monadresse@domaine.com";
	final String AUTRE_ADRESSE_COURRIEL_VALIDE = "masecondeadresse@autredomaine.com";

	private InformationsContact employe;

	@Before
	public void initialisation() {
		employe = new InformationsContact(ADRESSE_COURRIEL_VALIDE);
	}

	@Test
	public void etantDonneUnEmployeAvecUneAdresseCourrielValideQuandGetAdresseCourrielDevraitRetournerAdresseCourriel() {
		assertEquals(ADRESSE_COURRIEL_VALIDE, employe.getAdresseCourriel());
	}

	@Test(expected = AdresseCourrielInvalideException.class)
	public void etantDonneUnEmployeAvecUneAdresseCourrielInvalideQuandCreationDevraitLancerException() {
		final String ADRESSE_COURRIEL_INVALIDE = "CeciEstUneAdresseCourrielInvalide";
		new InformationsContact(ADRESSE_COURRIEL_INVALIDE);
	}

	@Test
	public void etantDonneDeuxEmployesAvecAdressesCourrielIdentiquesQuandEqualsDevraitRetournerVrai() {
		InformationsContact autreEmploye = new InformationsContact(ADRESSE_COURRIEL_VALIDE);
		assertTrue(employe.equals(autreEmploye));
	}

	@Test
	public void etantDonneDeuxEmployesAvecDeuxAdressesCourrielsDifferentesQuandEqualsDevraitRetournerFaux() {
		InformationsContact autreEmploye = new InformationsContact(AUTRE_ADRESSE_COURRIEL_VALIDE);
		assertFalse(employe.equals(autreEmploye));
	}

}
