package org.ClAssignateur.domaine.demandes;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

public interface DemandesEntrepot {

	public void persisterDemande(Demande demande);

	public List<Demande> obtenirDemandes();

	public List<Demande> obtenirDemandesSelonCourrielOrganisateur(String courrielOrganisateur);

	public Optional<Demande> obtenirDemandeSelonId(UUID id);

	public Optional<Demande> obtenirDemandeSelonTitre(String titre);

	public int taille();

	public void retirerDemande(Demande demande);

	public void vider();

}
