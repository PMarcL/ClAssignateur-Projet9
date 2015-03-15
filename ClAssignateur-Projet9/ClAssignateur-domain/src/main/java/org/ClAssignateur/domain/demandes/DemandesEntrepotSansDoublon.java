package org.ClAssignateur.domain.demandes;

import java.util.Optional;

public interface DemandesEntrepotSansDoublon {

	public void persisterDemande(Demande demande);

	public Optional<Demande> trouverDemandeSelonTitre(String titre);

	public void mettreAJourDemande(Demande demande);
}
