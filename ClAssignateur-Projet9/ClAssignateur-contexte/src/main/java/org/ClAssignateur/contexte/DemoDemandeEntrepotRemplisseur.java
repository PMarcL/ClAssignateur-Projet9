package org.ClAssignateur.contexte;

import java.util.UUID;
import java.util.ArrayList;

import org.ClAssignateur.domaine.contacts.ContactsReunion;
import org.ClAssignateur.domaine.contacts.InformationsContact;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;
import org.ClAssignateur.domaine.demandes.priorite.Priorite;
import org.ClAssignateur.domaine.salles.Salle;

public class DemoDemandeEntrepotRemplisseur {

	// TODO voir si cette classe est encore utile?
	public void remplir(DemandesEntrepot entrepot) {

		InformationsContact organisateur = new InformationsContact("organisateur@hotmail.com");
		InformationsContact responsable = new InformationsContact("responsable@hotmail.com");
		ContactsReunion groupe = new ContactsReunion(organisateur, responsable, new ArrayList<InformationsContact>());
		UUID id = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
		int nombreParticipants = 5;
		Demande demandeEnAttente = new Demande(id, nombreParticipants, groupe, "Demande demo", Priorite.basse());

		Demande demandeAssignee = new Demande(UUID.randomUUID(), nombreParticipants, groupe, "Demande demo2",
				Priorite.basse());
		Salle salle = new Salle(15, "A15");
		demandeAssignee.placerReservation(salle);

		entrepot.persisterDemande(demandeEnAttente);
		entrepot.persisterDemande(demandeAssignee);
	}
}
