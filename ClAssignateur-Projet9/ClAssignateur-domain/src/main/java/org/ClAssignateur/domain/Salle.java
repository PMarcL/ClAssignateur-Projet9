package org.ClAssignateur.domain;

public class Salle {

	private String _local;
	private int _capacite;

	public Salle(String local, int capacite) {
		this._local = local;
		this._capacite = capacite;
	}

	public String GetLocal() {
		return this._local;
	}

	public int GetCapacite() {
		return this._capacite;
	}
}
