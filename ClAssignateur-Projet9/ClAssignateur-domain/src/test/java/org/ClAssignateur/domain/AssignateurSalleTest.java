package org.ClAssignateur.domain;

import static org.mockito.Mockito.*;

import org.junit.Test;

public class AssignateurSalleTest {

	private final int FREQUENCE_QUELCONQUE = 5;
	private final int LIMITE_QUELCONQUE = 10;

	@Test
	public void etantDonneUnAssignateurSalleQuandDemandeAssignerDemandeSalleDevraitVerifierConditionsAssignation() {
		AssignateurSalle assignSalle = new AssignateurSalle(FREQUENCE_QUELCONQUE, LIMITE_QUELCONQUE);
		IStrategieDeclenchementAssignation strategieMock = mock(IStrategieDeclenchementAssignation.class);
		assignSalle.setStrategieDeclenchementAssignation(strategieMock);

		FileDemande fileDemandeMock = mock(FileDemande.class);
		EntrepotSalles entrepotSalleMock = mock(EntrepotSalles.class);
		assignSalle.assignerDemandeSalle(fileDemandeMock, entrepotSalleMock);

		verify(strategieMock).verifierConditionAtteinte(assignSalle);
	}

}
