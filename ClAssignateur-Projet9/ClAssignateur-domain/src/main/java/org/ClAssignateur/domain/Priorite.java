package org.ClAssignateur.domain;

public class Priorite {

	private static final int PRIORITE_BASSE = 1;
	private static final int PRIORITE_MOYENNE = 2;
	private static final int PRIORITE_HAUTE = 3;

	private int valeurPriorite;

	private Priorite(int valeurPriorite) {
		this.valeurPriorite = valeurPriorite;
	}

	public static Priorite basse() {
		return new Priorite(PRIORITE_BASSE);
	}

	public static Priorite moyenne() {
		return new Priorite(PRIORITE_MOYENNE);
	}

	public static Priorite haute() {
		return new Priorite(PRIORITE_HAUTE);
	}

	public boolean estPlusPrioritaire(Priorite priorite) {
		return this.valeurPriorite > priorite.valeurPriorite;
	}
}
