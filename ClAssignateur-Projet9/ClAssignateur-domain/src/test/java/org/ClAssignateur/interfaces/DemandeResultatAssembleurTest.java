package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.ClAssignateur.domain.demandes.Demande;
import org.junit.Test;

public class DemandeResultatAssembleurTest {

	private static final int NOMBRE_PARTICIPANTS = 10;
	private DemandeResultatAssembleur assembleur;

	@Test
	public void etantDonneUneDemandeAvecNombreParticipantsXQuandAssembleEstAppelerAlorsRetourneDemandeResultatAvecNombrePersonneX() {
		assembleur = new DemandeResultatAssembleur();
		Demande demande = mock(Demande.class);
		given(demande.getNbParticipants()).willReturn(NOMBRE_PARTICIPANTS);

		DemandeResultat demandeResultat = assembleur.assemblerDemande(demande);

		assertEquals(NOMBRE_PARTICIPANTS, demandeResultat.nombrePersonne);
	}
}
