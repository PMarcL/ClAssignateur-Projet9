package org.ClAssignateur.contexts;

import java.util.UUID;

import java.util.ArrayList;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.groupe.Groupe;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class DemoDemandeEntrepotRemplisseur {

	public void remplir(DemandesEntrepot entrepot) {
		Employe organisateur = new Employe("organisateur@hotmail.com");
		Employe responsable = new Employe("responsable@hotmail.com");
		Groupe groupe = new Groupe(organisateur, responsable, new ArrayList<Employe>());
		UUID id = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		Demande demande = new Demande(id, groupe, "Demande demo");
		entrepot.persisterDemande(demande);
	}
}
