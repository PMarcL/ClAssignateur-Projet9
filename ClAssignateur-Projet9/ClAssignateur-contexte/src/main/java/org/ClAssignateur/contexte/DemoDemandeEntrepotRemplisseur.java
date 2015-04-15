package org.ClAssignateur.contexte;

import java.util.UUID;
import java.util.ArrayList;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;
import org.ClAssignateur.domaine.groupe.Employe;
import org.ClAssignateur.domaine.groupe.Groupe;
import org.ClAssignateur.domaine.groupe.courriel.AdresseCourriel;
import org.ClAssignateur.domaine.salles.Salle;

public class DemoDemandeEntrepotRemplisseur {

	public void remplir(DemandesEntrepot entrepot) {

		Employe organisateur = creerEmploye("organisateur@hotmail.com");
		Employe responsable = creerEmploye("responsable@hotmail.com");
		Groupe groupe = new Groupe(organisateur, responsable, new ArrayList<Employe>());
		UUID id = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		Demande demandeEnAttente = new Demande(id, groupe, "Demande demo");

		Demande demandeAssignee = new Demande(UUID.randomUUID(), groupe, "Demande demo2");
		Salle salle = new Salle(15, "A15");
		demandeAssignee.placerReservation(salle);

		entrepot.persisterDemande(demandeEnAttente);
		entrepot.persisterDemande(demandeAssignee);
	}

	private Employe creerEmploye(String adresseCourriel) {
		AdresseCourriel courriel = new AdresseCourriel(adresseCourriel);
		return new Employe(courriel);
	}
}
