package org.ClAssignateur.domain.demandes;

import java.util.List;

import java.util.Optional;

public interface DemandesEntrepot {

	public void persisterDemande(Demande demande);

	public List<Demande> obtenirDemandes();

	public Optional<Demande> obtenirDemandeSelonTitre(String titre);

	public int taille();
}
