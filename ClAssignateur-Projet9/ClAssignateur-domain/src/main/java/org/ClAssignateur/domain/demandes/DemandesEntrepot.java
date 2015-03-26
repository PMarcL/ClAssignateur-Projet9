package org.ClAssignateur.domain.demandes;

import org.ClAssignateur.domain.groupe.Employe;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface DemandesEntrepot {

	public void persisterDemande(Demande demande);

	public List<Demande> obtenirDemandes();

	public Optional<Demande> obtenirDemandeSelonId(UUID id);

	public Optional<Demande> obtenirDemandeSelonTitre(String titre);

	public List<Demande> obtenirDemandesSelonOrganisateur(Employe organisateur);

	public int taille();

}
