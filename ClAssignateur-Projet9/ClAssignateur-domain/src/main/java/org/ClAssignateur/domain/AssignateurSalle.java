package org.ClAssignateur.domain;

import java.util.Optional;
import java.util.TimerTask;

public class AssignateurSalle extends TimerTask {

	private ConteneurDemandes demandes;
	private EntrepotSalles salles;
	private StrategieDeSelectionDeSalle strategieSelectionSalle;

	public AssignateurSalle(ConteneurDemandes demandes, EntrepotSalles salles) {
		this.demandes = demandes;
		this.salles = salles;
		strategieSelectionSalle = new SelectionnerSalleLaPlusPetiteRepondantADemande();
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
		for (Demande demandeCourante : demandes) {
			Optional<Salle> salle = salles.obtenirSalleRepondantDemande(strategieSelectionSalle, demandeCourante);

			if (salle.isPresent()) {
				salle.get().placerReservation(demandeCourante);
				salles.persister(salle.get());
			}

		}

		demandes.vider();
	}
}
