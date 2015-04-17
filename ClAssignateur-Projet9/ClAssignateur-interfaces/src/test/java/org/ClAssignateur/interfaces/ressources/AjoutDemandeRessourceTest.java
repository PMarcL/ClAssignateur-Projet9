package org.ClAssignateur.interfaces.ressources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.Before;

import java.util.UUID;

import org.ClAssignateur.domaine.contacts.AdresseCourrielInvalideException;
import org.ClAssignateur.interfaces.ressources.AjoutDemandeRessource;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;

public class AjoutDemandeRessourceTest {

	final private UUID DEMANDE_ID = UUID.randomUUID();
	private final String COURRIEL_ORGANISATEUR = "courrielOrganisateur@courriel.com";
	private final String EMPLACEMENT_DEMANDE_AJOUTE = "/demandes/" + COURRIEL_ORGANISATEUR + "/"
			+ DEMANDE_ID.toString();
	private final String MESSAGE_EMAIL_INVALIDE = "Le courriel \"" + COURRIEL_ORGANISATEUR + "\" n'est pas valide";

	private ReservationDemandeDTO demandeDTO;
	private ServiceReservationSalle service;

	private AjoutDemandeRessource ressource;

	@Before
	public void initialement() {
		demandeDTO = mock(ReservationDemandeDTO.class);
		demandeDTO.courrielOrganisateur = COURRIEL_ORGANISATEUR;
		service = mock(ServiceReservationSalle.class);
		given(service.ajouterDemande(demandeDTO)).willReturn(DEMANDE_ID);

		ressource = new AjoutDemandeRessource(service);
	}

	@Test
	public void lorsqueAjouterUneDemandeValideDevraitRetrouverStatusCREATED() {
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(Status.CREATED.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void lorsqueAjouterUneDemandeValideDevraitRetrouverLeBonEmplacementDeLaDemandeAjoute() {
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(EMPLACEMENT_DEMANDE_AJOUTE, reponse.getLocation().toString());
	}

	@Test
	public void lorsqueAjouterUneDemandeAvecCourrielInvalideDevraitRetrouverStatusBAD_REQUEST() {
		given(service.ajouterDemande(demandeDTO)).willThrow(new AdresseCourrielInvalideException(COURRIEL_ORGANISATEUR));
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void lorsqueAjouterUneDemandeAvecCourrielInvalideDevraitRetrouverMessageAvecCourrielInvalide() {
		given(service.ajouterDemande(demandeDTO)).willThrow(new AdresseCourrielInvalideException(COURRIEL_ORGANISATEUR));
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(MESSAGE_EMAIL_INVALIDE, reponse.getEntity());
	}

	@Test
	public void lorsqueAjouterUneDemandeLanceUneAutreExceptionDevraitRetournerStatusServerError() {
		given(service.ajouterDemande(demandeDTO)).willThrow(new RuntimeException());
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), reponse.getStatus());
	}
}
