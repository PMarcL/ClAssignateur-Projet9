package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrganisateurTest {

	private final String UN_NOM_QUELCONQUE = "John Doe";
	private final String UN_NOM_VIDE = "";

	private Organisateur organisateurATester;

	@Test
	public void unNouvelOrganisateurQuandCreeConnaitSonNom() {
		organisateurATester = new Organisateur(UN_NOM_QUELCONQUE);
		assertEquals(UN_NOM_QUELCONQUE, organisateurATester.getNom());
	}

	@Test(expected = IllegalArgumentException.class)
	public void unNouvelOrganisateurQuandCreeNePeutAvoirUnNomVide() {
		organisateurATester = new Organisateur(UN_NOM_VIDE);
	}

}
