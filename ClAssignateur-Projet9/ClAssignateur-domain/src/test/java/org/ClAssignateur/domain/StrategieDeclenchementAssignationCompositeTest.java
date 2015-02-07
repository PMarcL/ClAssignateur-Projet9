package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class StrategieDeclenchementAssignationCompositeTest {
	private final boolean RETOUR_CONDITION_NON_ATTEINTE = false;
	private final boolean RETOUR_CONDITION_ATTEINTE = true;
	private final IStrategieDeclenchementAssignationContexte CONTEXTE_MOCK = mock(IStrategieDeclenchementAssignationContexte.class);

	private StrategieDeclenchementAssignationComposite strategie;

	@Before
	public void initialement() {
		strategie = new StrategieDeclenchementAssignationComposite();
	}

	@Test
	public void etantDonneAucuneStrategieQuandVerifierDevraitNePasAutoriserAssignation() {
		boolean result = strategie.verifierConditionAtteinte(CONTEXTE_MOCK);
		assertFalse(result);
	}

	private void ajouterStrategieConditionNonAtteinte() {
		IStrategieDeclenchementAssignation strategieConditionNonAtteinte = mock(IStrategieDeclenchementAssignation.class);

		given(
				strategieConditionNonAtteinte
						.verifierConditionAtteinte(any(IStrategieDeclenchementAssignationContexte.class))).willReturn(
				RETOUR_CONDITION_NON_ATTEINTE);

		strategie.ajouterStrategie(strategieConditionNonAtteinte);
	}

	@Test
	public void etantDonneUneStrategieQuandConditionNonAtteinteDevraitNePasAutoriserAssignation() {
		ajouterStrategieConditionNonAtteinte();
		boolean result = strategie.verifierConditionAtteinte(CONTEXTE_MOCK);
		assertFalse(result);
	}

	private void ajouterStrategieConditionAtteinte() {
		IStrategieDeclenchementAssignation strategieConditionAtteinte = mock(IStrategieDeclenchementAssignation.class);

		given(
				strategieConditionAtteinte
						.verifierConditionAtteinte(any(IStrategieDeclenchementAssignationContexte.class))).willReturn(
				RETOUR_CONDITION_ATTEINTE);

		strategie.ajouterStrategie(strategieConditionAtteinte);
	}

	@Test
	public void etantDonneAucuneConditionAtteinteQuandVerifierDevraitNePasAutoriserAssignation() {
		ajouterStrategieConditionNonAtteinte();
		ajouterStrategieConditionNonAtteinte();

		boolean result = strategie.verifierConditionAtteinte(CONTEXTE_MOCK);

		assertFalse(result);
	}

	@Test
	public void etantDonneAuMoinsUneConditionAtteinteQuandVerifierDevraitAutoriserAssignation() {
		ajouterStrategieConditionNonAtteinte();
		ajouterStrategieConditionAtteinte();

		boolean result = strategie.verifierConditionAtteinte(CONTEXTE_MOCK);

		assertTrue(result);
	}

}
