package org.ClAssignateur.domain;

import java.util.ArrayList;
import java.util.List;

public class EntrepotSalles {

	List<Salle> salles = new ArrayList<Salle>();

	public boolean estVide() {
		return salles.isEmpty();
	}

	public void ranger(Salle salle) {
		int capacite = salle.getCapacite();
		int emplacementDeRangement = trouverEmplacementSalleEgaleOuSuperieurProcheACapaciteRecherche(capacite);
		salles.add(emplacementDeRangement, salle);
	}

	public Salle obtenirSalleRepondantADemande(Demande demande)
			throws AucunesSallesDisponiblesException {
		int NbParticipants = demande.getNbParticipant();
		int emplacementDeSallePotable = trouverEmplacementSalleEgaleOuSuperieurProcheACapaciteRecherche(NbParticipants);
		while (emplacementDeSallePotable < salles.size()) {
			Salle sallePotable = salles.get(emplacementDeSallePotable);

			if (sallePotable.estDisponible(demande)
					&& NbParticipants <= sallePotable.getCapacite()) {
				salles.remove(sallePotable);
				return sallePotable;
			} else {
				emplacementDeSallePotable++;
			}
		}
		throw new AucunesSallesDisponiblesException(demande);
	}

	private int trouverEmplacementSalleEgaleOuSuperieurProcheACapaciteRecherche(
			int capacite) {
		int curseurDeRecherche = (salles.size() / 2) - 1 + (salles.size() % 2);
		int porteeDeRechercheEnDessou = curseurDeRecherche;
		int porteeDeRechercheAuDessu = salles.size() / 2;

		int emplacementMeilleurSalle = salles.size();

		int nbElementTraites = salles.size();

		while (nbElementTraites > 0) {

			if (salles.get(curseurDeRecherche).getCapacite() == capacite) {
				return curseurDeRecherche;

			} else if (salles.get(curseurDeRecherche).getCapacite() > capacite) {
				emplacementMeilleurSalle = curseurDeRecherche;

				nbElementTraites -= porteeDeRechercheAuDessu + 1;

				curseurDeRecherche = curseurDeRecherche
						- (porteeDeRechercheEnDessou / 2)
						- (porteeDeRechercheEnDessou % 2);

				porteeDeRechercheEnDessou = (porteeDeRechercheEnDessou / 2) - 1
						+ (porteeDeRechercheEnDessou % 2);
				porteeDeRechercheAuDessu = porteeDeRechercheEnDessou / 2;
			} else if (salles.get(curseurDeRecherche).getCapacite() < capacite) {
				nbElementTraites -= porteeDeRechercheEnDessou + 1;

				curseurDeRecherche = curseurDeRecherche
						+ (porteeDeRechercheAuDessu / 2)
						+ (porteeDeRechercheAuDessu % 2);

				porteeDeRechercheEnDessou = (porteeDeRechercheAuDessu / 2) - 1
						+ (porteeDeRechercheAuDessu % 2);
				porteeDeRechercheAuDessu = porteeDeRechercheAuDessu / 2;
			}
		}

		return emplacementMeilleurSalle;
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