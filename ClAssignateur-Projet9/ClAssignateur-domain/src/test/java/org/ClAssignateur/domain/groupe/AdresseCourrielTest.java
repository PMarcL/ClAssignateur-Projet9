package org.ClAssignateur.domain.groupe;

import static org.junit.Assert.*;
import org.junit.Test;

public class AdresseCourrielTest {

	@Test
	public void etantDonneUneAdresseCourrielValideQuandToStringDevraitRetournerAdresseCourriel() {
		final String ADRESSE_COURRIEL_VALIDE = "monadresse@domain.com";
		AdresseCourriel adresseCourriel = new AdresseCourriel(ADRESSE_COURRIEL_VALIDE);
		assertEquals(ADRESSE_COURRIEL_VALIDE, adresseCourriel.toString());
	}

	@Test(expected = AdresseCourrielInvalideException.class)
	public void etantDonneuneAdresseCourrielInvalideQuandCreationDevraitLancerException() {
		final String ADRESSE_COURRIEL_INVALIDE = "CeciEstUneAdresseCourrielInvalide";
		new AdresseCourriel(ADRESSE_COURRIEL_INVALIDE);
	}
}
