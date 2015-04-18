package org.ClAssignateur.domaine.demandes.priorite;

import javax.persistence.Column;

import javax.persistence.Embeddable;

@Embeddable
public class Priorite {
	private final int NIVEAU_PRIORITE_MINIMUM = 1;

	private static final int PRIORITE_TRES_BASSE = 5;
	private static final int PRIORITE_BASSE = 4;
	private static final int PRIORITE_MOYENNE = 3;
	private static final int PRIORITE_HAUTE = 2;
	private static final int PRIORITE_TRES_HAUTE = 1;

	@Column
	private int niveauPriorite;

	public Priorite() {
		this.niveauPriorite = PRIORITE_BASSE;
	}

	public Priorite(int niveauPriorite) {
		this.niveauPriorite = niveauPriorite;

		if (this.niveauPriorite < NIVEAU_PRIORITE_MINIMUM)
			throw new NiveauPrioriteInvalideException();
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
		return this.niveauPriorite < priorite.niveauPriorite;
	}

	public int getNiveauPriorite() {
		return this.niveauPriorite;
	}

	@Override
	public boolean equals(Object autreObjet) {
		if (autreObjet == null) {
			return false;
		}
		if (!(autreObjet instanceof Priorite)) {
			return false;
		} else {
			Priorite autrePriorite = (Priorite) autreObjet;
			return this.niveauPriorite == autrePriorite.niveauPriorite;
		}
	}
}
