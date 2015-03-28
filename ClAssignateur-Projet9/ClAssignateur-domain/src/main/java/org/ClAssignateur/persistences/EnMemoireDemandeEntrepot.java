package org.ClAssignateur.persistences;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class EnMemoireDemandeEntrepot implements DemandesEntrepot {

	private ArrayList<Demande> demandes = new ArrayList<Demande>();

	@Override
	public void persisterDemande(Demande demande) {
		if (existePas(demande)) {
			demandes.add(demande);
		}
	}

	private boolean existePas(Demande demande) {
		return this.demandes.stream().noneMatch(demandeExistante -> demandeExistante.equals(demande));
	}

	@Override
	public List<Demande> obtenirDemandes() {
		return demandes;
	}

	@Override
	public Optional<Demande> obtenirDemandeSelonId(UUID id) {
		return demandes.stream().filter(x -> x.getID().equals(id)).findFirst();
	}

	@Override
	public Optional<Demande> obtenirDemandeSelonTitre(String titre) {
		return demandes.stream().filter(x -> x.getTitre().equals(titre)).findFirst();
	}

	@Override
	public int taille() {
		return demandes.size();
	}

	@Override
	public void retirerDemande(Demande demande) {
		int indexDeDemande = trouverIndexDe(demande);
		demandes.remove(indexDeDemande);
	}

	private int trouverIndexDe(Demande demande) {
		Optional<Demande> demandeTrouvee = trouverDemandeCorrespondant(demande);

		if (demandeTrouvee.isPresent()) {
			return demandes.indexOf(demandeTrouvee.get());
		} else {
			return -1;
		}
	}

	private Optional<Demande> trouverDemandeCorrespondant(Demande demande) {
		return this.demandes.stream().filter(demandeExistante -> demandeExistante.equals(demande)).findFirst();
	}

	@Override
	public void vider() {
		demandes.clear();
	}
}
