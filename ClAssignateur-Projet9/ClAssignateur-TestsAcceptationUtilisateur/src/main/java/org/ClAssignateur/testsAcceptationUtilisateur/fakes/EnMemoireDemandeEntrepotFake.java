package org.ClAssignateur.testsAcceptationUtilisateur.fakes;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.persistences.EnMemoireDemandeEntrepot;

public class EnMemoireDemandeEntrepotFake extends EnMemoireDemandeEntrepot {

	private Demande derniereDemandePersistee;

	@Override
	public void persisterDemande(Demande demande) {
		if (super.contientPasDemande(demande)) {
			super.persisterDemande(demande);
			derniereDemandePersistee = demande;
		}
	}

	public Demande getDerniereDemandePersistee() {
		return derniereDemandePersistee;
	}
}
