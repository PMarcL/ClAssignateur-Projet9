package org.ClAssignateur.domain;

import java.util.Optional;

public interface DemandesEntrepot {

	public void persisterDemande(Demande demande);

	public Optional<Demande> obtenirDemandeSelonTitre(String titre);
}
