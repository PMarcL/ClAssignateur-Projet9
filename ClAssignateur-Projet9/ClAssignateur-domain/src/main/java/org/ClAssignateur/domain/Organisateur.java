package org.ClAssignateur.domain;

public class Organisateur {

	private String nom;

	public Organisateur(String nom) {
		if (nom == "")
			throw new IllegalArgumentException(
					"Le nom de l'organisateur ne peut pas être vide.");

		this.nom = nom;
	}

	public String GetNom() {
		return this.nom;
	}

}
