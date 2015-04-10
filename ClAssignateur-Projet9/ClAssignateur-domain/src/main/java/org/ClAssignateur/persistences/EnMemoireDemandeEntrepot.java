package org.ClAssignateur.persistences;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class EnMemoireDemandeEntrepot implements DemandesEntrepot {

	private ArrayList<Demande> demandes = new ArrayList<Demande>();
	private Demande derniereDemandePersistee;

	public void persisterDemande(Demande demande) {
		if (contientPasDemande(demande)) {
			demandes.add(demande);
			derniereDemandePersistee = demande;
		}
	}

	private boolean contientPasDemande(Demande demande) {
		return this.demandes.stream().noneMatch(demandeExistante -> demandeExistante.equals(demande));
	}

	public List<Demande> obtenirDemandes() {
		return demandes;
	}

	public Optional<Demande> obtenirDemandeSelonId(UUID id) {
		return demandes.stream().filter(x -> x.getID().equals(id)).findFirst();
	}

	public Optional<Demande> obtenirDemandeSelonTitre(String titre) {
		return demandes.stream().filter(x -> x.getTitre().equals(titre)).findFirst();
	}

	public Optional<Demande> obtenirDemandeSelonCourrielOrganisateurEtId(String courriel, UUID id) {
		return demandes.stream().filter(x -> x.getID().equals(id) && x.getOrganisateur().courriel.equals(courriel))
				.findFirst();
	}

	public int taille() {
		return demandes.size();
	}

	public void retirerDemande(Demande demande) {
		int indexDeDemande = trouverIndexDe(demande);
		if (indexDeDemande != -1) {
			demandes.remove(indexDeDemande);
		}
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
		demandes = new ArrayList<>();
	}

	@Override
	public List<Demande> obtenirDemandesSelonCourriel(String courriel) {
		return demandes.stream().filter(x -> x.getOrganisateur().courriel.equals(courriel))
				.collect(Collectors.toList());
	}

	public Demande getDerniereDemandePersistee() {
		return derniereDemandePersistee;
	}
}
