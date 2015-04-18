package org.ClAssignateur.persistance;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;

public class EnMemoireDemandeEntrepot implements DemandesEntrepot {

	private ArrayList<Demande> demandes = new ArrayList<Demande>();

	public void persisterDemande(Demande demande) {
		if (contientPasDemande(demande)) {
			this.demandes.add(demande);
		}
	}

	protected boolean contientPasDemande(Demande demande) {
		return !(this.demandes.contains(demande));
	}

	public List<Demande> obtenirDemandes() {
		return this.demandes;
	}

	// TODO refactorer les fonction ci-bas
	public Optional<Demande> obtenirDemandeSelonId(UUID id) {
		return this.demandes.stream().filter(demandeCourante -> demandeCourante.getID().equals(id)).findFirst();
	}

	public Optional<Demande> obtenirDemandeSelonTitre(String titre) {
		return this.demandes.stream().filter(demandeCourante -> demandeCourante.getTitre().equals(titre)).findFirst();
	}

	public int taille() {
		return this.demandes.size();
	}

	public void retirerDemande(Demande demande) {
		int indexDemande = trouverIndexDemande(demande);
		if (indexDemande != -1) {
			this.demandes.remove(indexDemande);
		}
	}

	private int trouverIndexDemande(Demande demande) {
		Optional<Demande> demandeTrouvee = trouverDemandeCorrespondant(demande);

		if (demandeTrouvee.isPresent()) {
			return this.demandes.indexOf(demandeTrouvee.get());
		} else {
			return -1;
		}
	}

	private Optional<Demande> trouverDemandeCorrespondant(Demande demande) {
		return this.demandes.stream().filter(demandeExistante -> demandeExistante.equals(demande)).findFirst();
	}

	@Override
	public void vider() {
		this.demandes = new ArrayList<>();
	}

	@Override
	public List<Demande> obtenirDemandesSelonCourrielOrganisateur(String courriel) {
		return this.demandes.stream().filter(x -> x.getCourrielOrganisateur().equals(courriel))
				.collect(Collectors.toList());
	}
}
