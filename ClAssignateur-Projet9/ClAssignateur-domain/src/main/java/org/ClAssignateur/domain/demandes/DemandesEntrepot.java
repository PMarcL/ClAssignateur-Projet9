package org.ClAssignateur.domain.demandes;

import java.util.UUID;

import java.util.List;
import java.util.Optional;

public interface DemandesEntrepot {

	public void persisterDemande(Demande demande);

	public List<Demande> obtenirDemandes();

	public Optional<Demande> obtenirDemandeSelonId(UUID id);

	public Optional<Demande> obtenirDemandeSelonTitre(String titre);

	public Optional<Demande> obtenirDemandeSelonCourrielOrganisateur(String courriel);

	public int taille();

	public void retirerDemande(Demande demande);

	public void vider();

}
