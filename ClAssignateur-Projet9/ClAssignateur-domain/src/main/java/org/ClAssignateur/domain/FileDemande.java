package org.ClAssignateur.domain;

import java.util.LinkedList;

import java.util.Queue;
import java.util.Date;

public class FileDemande {

	private Queue<Demande> _file = new LinkedList<Demande>();

	public int Taille() {
		return this._file.size();
	}

	public void Ajouter(Date dateDebut, Date dateFin, int nombreParticipant, Organisateur organisateur) {
		Demande demandeAjoutee = new Demande(dateDebut, dateFin, nombreParticipant, organisateur);
		
		this._file.offer(demandeAjoutee);
	}

	public void Vider() {
		this._file.clear();
	}

	public Demande Retirer() {
		return this._file.poll();
	}

}
