package org.ClAssignateur.domaine.groupe.courriel;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class AdresseCourriel {

	private InternetAddress adresseCourriel;

	public AdresseCourriel(String adresseCourriel) throws AdresseCourrielInvalideException {
		try {
			this.adresseCourriel = new InternetAddress(adresseCourriel);
			this.adresseCourriel.validate();
		} catch (AddressException exception) {
			throw new AdresseCourrielInvalideException();
		}
	}

	@Override
	public String toString() {
		return adresseCourriel.getAddress();
	}

	public boolean equals(Object autreAdresseCourriel) {
		return this.toString().equals(autreAdresseCourriel.toString());
	}
}
