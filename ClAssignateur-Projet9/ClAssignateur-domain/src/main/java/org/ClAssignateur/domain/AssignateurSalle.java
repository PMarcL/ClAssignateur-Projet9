package org.ClAssignateur.domain;

public class AssignateurSalle {

	public void assignerDemandeSalle(ConteneurDemande demandes,
			EntrepotSalles salles) {
		int nbDemandes = demandes.taille();
		for (int i = 0; i < nbDemandes; i++) {

			Demande demandeCourante;
			try {
				demandeCourante = demandes.retirer();
			} catch (Exception e) {
				return;
			}

			try {
				Salle salleDisponible = salles
						.obtenirSalleRepondantADemande(demandeCourante);
				salleDisponible.placerReservation(demandeCourante);
				salles.ranger(salleDisponible);
			} catch (Exception e) {
				demandes.ajouter(demandeCourante);
			}
		}
	}
}
