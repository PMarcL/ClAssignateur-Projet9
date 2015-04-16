package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.persistance.EnMemoireDemandeEntrepot;

public class EnMemoireDemandesEntrepotFake extends EnMemoireDemandeEntrepot {

	private Demande derniereDemandePersistee = null;

	@Override
	public void persisterDemande(Demande demande) {
		if (contientPasDemande(demande)) {
			super.persisterDemande(demande);
			derniereDemandePersistee = demande;
		}
	}

	public Demande getDerniereDemandePersistee() {
		return derniereDemandePersistee;
	}
}
