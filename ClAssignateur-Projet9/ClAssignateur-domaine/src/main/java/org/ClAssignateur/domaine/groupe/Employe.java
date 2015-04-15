package org.ClAssignateur.domaine.groupe;

import org.ClAssignateur.domaine.groupe.courriel.AdresseCourriel;

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
