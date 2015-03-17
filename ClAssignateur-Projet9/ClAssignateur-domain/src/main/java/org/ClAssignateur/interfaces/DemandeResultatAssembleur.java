package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.demandes.Demande;

public class DemandeResultatAssembleur {

	public DemandeResultat Assembler(Demande demande) {
		int nombrePersonne = demande.getNbParticipants();
		return new DemandeResultat(nombrePersonne);
	}

}
