package org.ClAssignateur.domain.groupe;

import static org.junit.Assert.*;

import org.junit.Before;

import org.junit.Test;

public class AdresseCourrielTest {
	final String ADRESSE_COURRIEL_VALIDE = "monadresse@domaine.com";
	final String AUTRE_ADRESSE_COURRIEL_VALIDE = "masecondeadresse@autredomaine.com";

	private AdresseCourriel adresseCourriel;

	@Before
	public void initialisation() {
		adresseCourriel = new AdresseCourriel(ADRESSE_COURRIEL_VALIDE);
	}

	@Test
	public void etantDonneUneAdresseCourrielValideQuandToStringDevraitRetournerAdresseCourriel() {
		assertEquals(ADRESSE_COURRIEL_VALIDE, adresseCourriel.toString());
	}

	@Test(expected = AdresseCourrielInvalideException.class)
	public void etantDonneuneAdresseCourrielInvalideQuandCreationDevraitLancerException() {
		final String ADRESSE_COURRIEL_INVALIDE = "CeciEstUneAdresseCourrielInvalide";
		new AdresseCourriel(ADRESSE_COURRIEL_INVALIDE);
	}

	@Test
	public void etantDonneDeuxAdressesCourrielIdentiquesQuandEqualsDevraitRetournerVrai() {
		AdresseCourriel autreAdresseCourriel = new AdresseCourriel(ADRESSE_COURRIEL_VALIDE);
		assertTrue(adresseCourriel.equals(autreAdresseCourriel));
	}

	@Test
	public void etantDonneDeuxAdressesCourrielsDifferentesQuandEqualsDevraitRetournerFaux() {
		AdresseCourriel autreAdresseCourriel = new AdresseCourriel(AUTRE_ADRESSE_COURRIEL_VALIDE);
		assertFalse(adresseCourriel.equals(autreAdresseCourriel));
	}

	@Test
	public void etantDonneUneAdresseCourrielEnChaineDeCaractereIdentiqueQuandEqualsDevraitRetournerVrai() {
		assertTrue(adresseCourriel.equals(ADRESSE_COURRIEL_VALIDE));
	}

	@Test
	public void etantDonneUneAdresseCourrielEnChaineDeCaracterDifferanteQuandEqualsDevraitRetournerFaux() {
		assertFalse(adresseCourriel.equals(AUTRE_ADRESSE_COURRIEL_VALIDE));
	}
}
