package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;

public class EnMemoireDemandeEntrepot implements DemandesEntrepot {

	private ArrayList<Demande> demandes = new ArrayList<Demande>();
	private Demande derniereDemandePersistee = null;

	public void persisterDemande(Demande demande) {
		if (contientPasDemande(demande)) {
			demandes.add(demande);
			derniereDemandePersistee = demande;
		}
	}

	private boolean contientPasDemande(Demande demande) {
		return !(this.demandes.contains(demande));
	}

	public List<Demande> obtenirDemandes() {
		return demandes;
	}

	// TODO refactorer les fonction ci-bas
	public Optional<Demande> obtenirDemandeSelonId(UUID id) {
		return demandes.stream().filter(demandeCourante -> demandeCourante.getID().equals(id)).findFirst();
	}

	public Optional<Demande> obtenirDemandeSelonTitre(String titre) {
		return demandes.stream().filter(demandeCourante -> demandeCourante.getTitre().equals(titre)).findFirst();
	}

	public Optional<Demande> obtenirDemandeSelonCourrielOrganisateurEtId(String courriel, UUID id) {
		return demandes
				.stream()
				.filter(demandeCourante -> demandeCourante.getID().equals(id)
						&& demandeCourante.getCourrielOrganisateur().equals(courriel)).findFirst();
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
		return demandes.stream().filter(x -> x.getCourrielOrganisateur().equals(courriel)).collect(Collectors.toList());
	}

	public Demande getDerniereDemandePersistee() {
		return derniereDemandePersistee;
	}
}
