package org.ClAssignateur.domain;

import java.util.Collection;
import java.util.Optional;
import java.util.TimerTask;

public class AssignateurSalle extends TimerTask {

	private ConteneurDemandes demandes;
	private EntrepotSalles entrepotSalles;
	private StrategieDeSelectionDeSalle strategieSelectionSalle;

	public AssignateurSalle(ConteneurDemandes demandes, EntrepotSalles entrepotSalles,
			StrategieDeSelectionDeSalle strategieSelectionSalle) {
		this.demandes = demandes;
		this.entrepotSalles = entrepotSalles;
		this.strategieSelectionSalle = strategieSelectionSalle;
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
			Optional<Salle> salle = strategieSelectionSalle.appliquer(salles, demandeCourante);

			if (salle.isPresent()) {
				salle.get().placerReservation(demandeCourante);
				entrepotSalles.persister(salle.get());
			}

		}

		demandes.vider();
	}
}
