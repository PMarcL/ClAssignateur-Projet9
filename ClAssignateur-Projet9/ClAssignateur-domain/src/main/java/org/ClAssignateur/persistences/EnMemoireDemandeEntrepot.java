package org.ClAssignateur.persistences;

import java.util.stream.Collectors;

import org.ClAssignateur.domain.groupe.Employe;
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
	public List<Demande> obtenirDemandesSelonOrganisateur(Employe organisateur) {
		return demandes.stream().filter(x -> x.getOrganisateur().equals(organisateur)).collect(Collectors.toList());
	}

	@Override
	public int taille() {
		return demandes.size();
	}

	private boolean existePas(Demande demande) {
		return this.demandes.stream().noneMatch(demandeExistante -> demandeExistante.equals(demande));
	}

}
