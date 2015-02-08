package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class StrategieDelaiDepasseTest {

	private final int FREQUENCE = 5;

	private StrategieDelaiDepasse strategie;
	private DeclencheurAssignationSalle contexteMock;

	@Before
	public void initialement() {
		strategie = new StrategieDelaiDepasse();
		contexteMock = mock(DeclencheurAssignationSalle.class);
		given(contexteMock.getFrequence()).willReturn(FREQUENCE);
	}

	@Test
	public void etantDonneDelaiNonAtteintQuandVerifierDevraitNePasAutoriserAssignation() {
		given(contexteMock.getDerniereAssignation()).willReturn(delaiNonAtteint());
		boolean result = strategie.verifierConditionAtteinte(contexteMock);
		assertFalse(result);
	}

	private Calendar delaiNonAtteint() {
		Calendar delaiNonAtteint = Calendar.getInstance();
		delaiNonAtteint.add(Calendar.MINUTE, -(FREQUENCE - 1));
		return delaiNonAtteint;
	}

	@Test
	public void etantDonneDelaiAtteintQuandVerifierAutoriserAssignation() {
		given(contexteMock.getDerniereAssignation()).willReturn(delaiAtteint());
		boolean result = strategie.verifierConditionAtteinte(contexteMock);
		assertTrue(result);
	}

	private Calendar delaiAtteint() {
		Calendar delaiAtteint = Calendar.getInstance();
		delaiAtteint.add(Calendar.MINUTE, -FREQUENCE);
		return delaiAtteint;
	}

	@Test
	public void etantDonneDelaiDepasseQuandVerifierDevraitAutoriserAssignation() {
		given(contexteMock.getDerniereAssignation()).willReturn(delaiDepasse());
		boolean result = strategie.verifierConditionAtteinte(contexteMock);
		assertTrue(result);
	}

	private Calendar delaiDepasse() {
		Calendar delaiDepasse = Calendar.getInstance();
		delaiDepasse.add(Calendar.MINUTE, -(FREQUENCE + 1));
		return delaiDepasse;
	}

}
