package org.ClAssignateur.services.localisateur;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LocalisateurServicesTest {

	private final ServiceTest SERVICE_TEST = new ServiceTestImplementation();

	@Before
	public void initialisation() {
		LocalisateurServices.getInstance().remettreAZero();
	}

	@Test
	public void quandEnregistreServiceDevraitPouvoirObtenirService() {
		LocalisateurServices.getInstance().enregistrer(ServiceTest.class, SERVICE_TEST);
		ServiceTest serviceResultat = LocalisateurServices.getInstance().obtenir(ServiceTest.class);
		assertEquals(SERVICE_TEST, serviceResultat);
	}

	@Test(expected = ServiceIntrouvableException.class)
	public void etantDonneServiceNonEnregistreQuandObtenirDevraitLancerException() {
		LocalisateurServices.getInstance().obtenir(ServiceTest.class);
	}

	@Test(expected = ServiceDoublonException.class)
	public void etantDonneServiceDejaEnregistreQuandEnregistrerDevraitLancerException() {
		LocalisateurServices.getInstance().enregistrer(ServiceTest.class, SERVICE_TEST);
		LocalisateurServices.getInstance().enregistrer(ServiceTest.class, new ServiceTestImplementation());
	}

	private interface ServiceTest {

	}

	private class ServiceTestImplementation implements ServiceTest {

	}
}
