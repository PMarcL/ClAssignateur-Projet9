package org.ClAssignateur.services.reservations.minuterie;

public class Minute {

	private int NB_MILLISECONDES_1_MINUTE = 60000;

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

	public int getDelaiMillisecondes() {
		return this.valeur * NB_MILLISECONDES_1_MINUTE;
	}
}
