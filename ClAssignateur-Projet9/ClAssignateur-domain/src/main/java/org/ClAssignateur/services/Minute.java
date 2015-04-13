package org.ClAssignateur.services;

public class Minute {

	public final int valeur;

	public Minute(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Minute))
			return false;

		Minute minuteAComparer = (Minute) obj;
		return (this.valeur == minuteAComparer.valeur);
	}
}
