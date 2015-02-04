package org.ClAssignateur.domain;

import java.util.ArrayList;
import java.util.List;

public class EntrepotSalles {

	List<Salle> salles = new ArrayList<Salle>();

	public boolean EstVide() {
		return salles.isEmpty();
	}

	public void Ranger(Salle salle) {
		salles.add(salle);
	}

	public Salle ObtenirSalleRepondantADemande(Demande demande)
			throws AucunesSallesDisponiblesException {
		for (Salle salle : salles) {
			if (salle.estDisponible(demande)) {
				salles.remove(salle);
				return salle;
			}
		}
		throw new AucunesSallesDisponiblesException(demande);
	}

}

class AucunesSallesDisponiblesException extends Exception {
	private static final long serialVersionUID = 1L;

	public AucunesSallesDisponiblesException(Demande demande) {
		System.out
				.println("Vous n'avez pas réussi à trouver de local disponible pouvant contenir "
						+ demande.getNbParticipant()
						+ " participants pour la demande de "
						+ demande.getOrganisateur());
	}
}