package org.ClAssignateur.domain.groupe;

public class Employe {

	public AdresseCourriel courriel;

	public Employe(AdresseCourriel courriel) {
		this.courriel = courriel;
	}

	public boolean equals(Object o) {
		if (!(o instanceof Employe)) {
			return false;
		}

		Employe employe = (Employe) o;
		return this.courriel.equals(employe.courriel);
	}

}
