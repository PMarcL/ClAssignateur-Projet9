package org.ClAssignateur.domain.demandes;

public class Priorite {

	private static final int PRIORITE_TRES_BASSE = 1;
	private static final int PRIORITE_BASSE = 2;
	private static final int PRIORITE_MOYENNE = 3;
	private static final int PRIORITE_HAUTE = 4;
	private static final int PRIORITE_TRES_HAUTE = 5;

	private int valeurPriorite;

	private Priorite(int valeurPriorite) {
		this.valeurPriorite = valeurPriorite;
	}

	public static Priorite tresBasse() {
		return new Priorite(PRIORITE_TRES_BASSE);
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

	public static Priorite tresHaute() {
		return new Priorite(PRIORITE_TRES_HAUTE);
	}

	public boolean estPlusPrioritaire(Priorite priorite) {
		return this.valeurPriorite > priorite.valeurPriorite;
	}
}
