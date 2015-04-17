package org.ClAssignateur.domaine.contacts;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class InformationsContact {

	private InternetAddress adresseCourriel;

	public InformationsContact(String courriel) throws AdresseCourrielInvalideException {
		try {
			this.adresseCourriel = new InternetAddress(courriel);
			this.adresseCourriel.validate();
		} catch (AddressException exception) {
			throw new AdresseCourrielInvalideException(courriel);
		}
	}

	public String getAdresseCourriel() {
		return adresseCourriel.getAddress();
	}

	public boolean equals(Object o) {
		if (!(o instanceof InformationsContact)) {
			return false;
		}

		InformationsContact employe = (InformationsContact) o;
		return this.adresseCourriel.equals(employe.adresseCourriel);
	}

}
