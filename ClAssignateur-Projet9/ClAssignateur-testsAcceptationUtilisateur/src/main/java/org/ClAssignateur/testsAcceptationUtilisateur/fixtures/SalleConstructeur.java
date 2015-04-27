package org.ClAssignateur.testsAcceptationUtilisateur.fixtures;

import org.ClAssignateur.domaine.salles.Salle;

public class SalleConstructeur {
	private final int CAPACITE_PAR_DEFAUT = 25;
	private final String NOM_PAR_DEFAUT = "Salle inconnue";

	private int capacite;

	public SalleConstructeur() {
		this.capacite = CAPACITE_PAR_DEFAUT;
	}

	public SalleConstructeur capacite(int capacite) {
		this.capacite = capacite;
		return this;
	}

	public Salle construireSalle() {
		return new Salle(this.capacite, NOM_PAR_DEFAUT);
	}
}
