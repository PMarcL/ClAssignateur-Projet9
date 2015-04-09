package org.ClAssignateur.domain.groupe;

import javax.mail.internet.AddressException;

import javax.mail.internet.InternetAddress;

public class AdresseCourriel {

	private InternetAddress adresseCourriel;

	public AdresseCourriel(String adresseCourriel) throws AdresseCourrielInvalideException {
		try {
			this.adresseCourriel = new InternetAddress(adresseCourriel);
			this.adresseCourriel.validate();
		} catch (AddressException exception) {
			throw new AdresseCourrielInvalideException(exception.getMessage());
		}
	}

	@Override
	public String toString() {
		return this.adresseCourriel.getAddress();
	}
}
