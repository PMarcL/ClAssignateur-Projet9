package org.ClAssignateur.interfaces.ressources;

import org.ClAssignateur.domain.groupe.AdresseCourrielInvalideException;

import java.util.UUID;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.interfaces.dto.ReservationDemandeDTO;
import org.ClAssignateur.interfaces.dto.assembleur.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.interfaces.ressources.AjoutDemandeRessource;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Before;

public class AjoutDemandeRessourceTest {

	final private UUID DEMANDE_ID = UUID.randomUUID();
	private final String COURRIEL_ORGANISATEUR = "courrielOrganisateur@courriel.com";
	private final String EMPLACEMENT_DEMANDE_AJOUTE = "/demandes/" + COURRIEL_ORGANISATEUR + "/"
			+ DEMANDE_ID.toString();
	private final String MESSAGE_EMAIL_INVALIDE = "Le courriel " + COURRIEL_ORGANISATEUR + " n'est pas valide";

	private ReservationDemandeDTO demandeDTO;
	private ServiceInformationsDemande service;
	private ReservationDemandeDTOAssembleur reservationDemandeAssembleur;
	private Demande demande;

	private AjoutDemandeRessource ressource;

	@Before
	public void initialement() {
		demandeDTO = mock(ReservationDemandeDTO.class);
		demandeDTO.courrielOrganisateur = COURRIEL_ORGANISATEUR;
		service = mock(ServiceInformationsDemande.class);
		reservationDemandeAssembleur = mock(ReservationDemandeDTOAssembleur.class);
		demande = mock(Demande.class);
		given(demande.getID()).willReturn(DEMANDE_ID);

		ressource = new AjoutDemandeRessource(service, reservationDemandeAssembleur);
	}

	@Test
	public void lorsqueAjouterUneDemandeValideDevraitRetrouverStatusCREATED() {
		given(reservationDemandeAssembleur.assemblerDemande(demandeDTO)).willReturn(demande);
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(Status.CREATED.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void lorsqueAjouterUneDemandeValideDevraitRetrouverLeBonEmplacementDeLaDemandeAjoute() {
		given(reservationDemandeAssembleur.assemblerDemande(demandeDTO)).willReturn(demande);
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(EMPLACEMENT_DEMANDE_AJOUTE, reponse.getLocation().toString());
	}

	@Test
	public void lorsqueAjouterUneDemandeAvecCourrielInvalideDevraitRetrouverStatusBAD_REQUEST() {
		given(reservationDemandeAssembleur.assemblerDemande(demandeDTO)).willThrow(
				new AdresseCourrielInvalideException());
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void lorsqueAjouterUneDemandeAvecCourrielInvalideDevraitRetrouverMessageAvecCourrielInvalide() {
		given(reservationDemandeAssembleur.assemblerDemande(demandeDTO)).willThrow(
				new AdresseCourrielInvalideException());
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(MESSAGE_EMAIL_INVALIDE, reponse.getEntity());
	}
}
