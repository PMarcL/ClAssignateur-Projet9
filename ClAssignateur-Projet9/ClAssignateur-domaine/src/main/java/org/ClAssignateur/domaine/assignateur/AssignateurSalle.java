package org.ClAssignateur.domaine.assignateur;

import org.ClAssignateur.domaine.assignateur.strategies.SelectionSalleStrategie;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.domaine.salles.Salle;
import org.ClAssignateur.domaine.salles.SallesEntrepot;
import java.util.Collection;
import java.util.Optional;
import java.util.List;

public class AssignateurSalle {

	private SallesEntrepot entrepotSalles;
	private SelectionSalleStrategie selectionSalleStrategie;

	public AssignateurSalle(SallesEntrepot salles, SelectionSalleStrategie strategieSelectionSalle) {
		this.entrepotSalles = salles;
		this.selectionSalleStrategie = strategieSelectionSalle;
	}

	public void lancerAssignation(ConteneurDemandes demandes, Notificateur notificateur) {
		Collection<Salle> salles = entrepotSalles.obtenirSalles();
		List<Demande> demandesEnAttente = demandes.obtenirDemandesEnAttenteOrdrePrioritaire();

		for (Demande demandeCourante : demandesEnAttente) {
			Optional<Salle> salle = selectionSalleStrategie.selectionnerSalle(salles, demandeCourante);

			if (salle.isPresent()) {
				reserverSalle(notificateur, demandeCourante, salle.get());
				demandes.archiverDemande(demandeCourante);
			} else {
				notificateur.notifierEchec(demandeCourante);
			}
		}
	}

	private void reserverSalle(Notificateur notificateur, Demande demandeCourante, Salle salle) {
		demandeCourante.placerReservation(salle);
		notificateur.notifierSucces(demandeCourante, salle);
	}

}
