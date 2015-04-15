package org.ClAssignateur.domaine.groupe;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Employe {

	private InternetAddress adresseCourriel;

	public Employe(String courriel) throws AdresseCourrielInvalideException {
		try {
			this.adresseCourriel = new InternetAddress(courriel);
			this.adresseCourriel.validate();
		} catch (AddressException exception) {
			throw new AdresseCourrielInvalideException();
		}
	}

	public String getAdresseCourriel() {
		return adresseCourriel.getAddress();
	}

	public boolean equals(Object o) {
		if (!(o instanceof Employe)) {
			return false;
		}

		Employe employe = (Employe) o;
		return this.adresseCourriel.equals(employe.adresseCourriel);
	}

}
