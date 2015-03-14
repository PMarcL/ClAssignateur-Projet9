package org.ClAssignateur.domain;

import java.util.Collection;
import java.util.Optional;
import java.util.TimerTask;

public class AssignateurSalle extends TimerTask {

	private ConteneurDemandes demandes;
	private EntrepotSalles entrepotSalles;
	private SelectionSalleStrategie selectionSalleStrategie;

	public AssignateurSalle(ConteneurDemandes demandes, EntrepotSalles entrepotSalles,
			SelectionSalleStrategie strategieSelectionSalle) {
		this.demandes = demandes;
		this.entrepotSalles = entrepotSalles;
		this.selectionSalleStrategie = strategieSelectionSalle;
	}

	public void ajouterDemande(Demande demande) {
		demandes.ajouterDemande(demande);
	}

	public void assignerDemandeSalleSiContientAuMoins(int nombreDemandes) {
		if (demandes.contientAuMoins(nombreDemandes))
			assignerDemandeSalle();
	}

	@Override
	public void run() {
		assignerDemandeSalle();
	}

	private void assignerDemandeSalle() {
		Collection<Salle> salles = entrepotSalles.obtenirSalles();

		for (Demande demandeCourante : demandes) {
			Optional<Salle> salle = selectionSalleStrategie.selectionnerSalle(salles, demandeCourante);

			if (salle.isPresent()) {
				salle.get().placerReservation(demandeCourante);
				entrepotSalles.persister(salle.get());
			}

		}

		demandes.vider();
	}
}
