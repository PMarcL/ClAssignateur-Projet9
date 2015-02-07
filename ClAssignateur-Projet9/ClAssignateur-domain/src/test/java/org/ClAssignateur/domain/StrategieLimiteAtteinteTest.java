package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class StrategieLimiteAtteinteTest {

	private final int LIMITE_COURANTE = 10;
	private final int LIMITE_DEPASSEE = LIMITE_COURANTE + 1;
	private final int LIMITE_NON_ATTEINTE = LIMITE_COURANTE - 1;

	private StrategieLimiteAtteinte strategie;
	private IStrategieDeclenchementAssignationContexte contexteMock;

	@Before
	public void initialement() {
		strategie = new StrategieLimiteAtteinte();
		contexteMock = mock(IStrategieDeclenchementAssignationContexte.class);
		given(contexteMock.getLimite()).willReturn(LIMITE_COURANTE);
	}

	@Test
	public void etantDonneLimiteDepasseeQuandVerifierDevraitAutoriserAssignation() {
		given(contexteMock.getNbDemandesAssignationCourantes()).willReturn(LIMITE_DEPASSEE);
		boolean result = strategie.verifierConditionAtteinte(contexteMock);
		assertTrue(result);
	}

	@Test
	public void etantDonneLimiteNonAtteinteQuandVerifierDevraitNePasAutoriserAssignation() {
		given(contexteMock.getNbDemandesAssignationCourantes()).willReturn(LIMITE_NON_ATTEINTE);
		boolean result = strategie.verifierConditionAtteinte(contexteMock);
		assertFalse(result);
	}

	@Test
	public void quandLimiteAtteinteDevraitAutoriserAssignation() {
		given(contexteMock.getNbDemandesAssignationCourantes()).willReturn(LIMITE_COURANTE);
		boolean result = strategie.verifierConditionAtteinte(contexteMock);
		assertTrue(result);
	}

}
