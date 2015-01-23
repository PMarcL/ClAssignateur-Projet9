package org.ClAssignateur.domain;

public class Salle {

	private String local;
	private int capacite;

	public Salle(String local, int capacite) {
		this.local = local;
		this.capacite = capacite;
	}

	public String GetLocal() {
		return this.local;
	}

	public int GetCapacite() {
		return this.capacite;
	}
}
