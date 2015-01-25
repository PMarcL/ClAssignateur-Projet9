package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class OrganisateurTest {

	private final String UN_NOM_QUELCONQUE = "John Doe";

	@Test
	public void unNouvelOrganisateurQuandCreeConnaitSonNom() {
		Organisateur organisateurATester = new Organisateur(UN_NOM_QUELCONQUE);
		assertEquals(UN_NOM_QUELCONQUE, organisateurATester.GetNom());
	}

}
