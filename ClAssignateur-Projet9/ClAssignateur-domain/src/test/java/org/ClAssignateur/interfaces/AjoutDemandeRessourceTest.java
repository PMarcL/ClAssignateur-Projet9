package org.ClAssignateur.interfaces;

import org.ClAssignateur.domain.groupe.AdresseCourrielInvalideException;

import java.net.URI;
import java.util.UUID;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response;
import org.junit.Test;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.services.ServiceDemande;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.junit.Before;

public class AjoutDemandeRessourceTest {

	final private UUID DEMANDE_ID = UUID.randomUUID();
	private final String COURRIEL_ORGANISATEUR = "courrielOrganisateur@courriel.com";
	private final AdresseCourrielInvalideException COURRIEL_INVALIDE_EXCEPTION = new AdresseCourrielInvalideException("ERREUR_COURRIEL");
	private final String EMPLACEMENT_DEMANDE_AJOUTE = "/demandes/" + COURRIEL_ORGANISATEUR + "/" + DEMANDE_ID.toString();
	
	private ReservationDemandeDTO demandeDTO;
	private ServiceDemande service;
	private ReservationDemandeDTOAssembleur reservationDemandeAssembleur;
	private Demande demande;
	
	private AjoutDemandeRessource ressource;
	
	@Before
	public void initialement() {
		demandeDTO = mock(ReservationDemandeDTO.class);
		demandeDTO.courrielOrganisateur = COURRIEL_ORGANISATEUR;
		service = mock(ServiceDemande.class);
		reservationDemandeAssembleur = mock(ReservationDemandeDTOAssembleur.class);
		demande = mock(Demande.class);
		given(demande.getID()).willReturn(DEMANDE_ID);
		
		ressource = new AjoutDemandeRessource(service, reservationDemandeAssembleur);
	}
	
	@Test
	public void lorsqueAjouterUneDemandeValideDevraitRetrouverStatusCREATED(){
		given(reservationDemandeAssembleur.assemblerDemande(demandeDTO)).willReturn(demande);
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(Status.CREATED.getStatusCode(), reponse.getStatus());
	}
	
	@Test
	public void lorsqueAjouterUneDemandeValideDevraitRetrouverLeBonEmplacementDeLaDemandeAjoute(){
		given(reservationDemandeAssembleur.assemblerDemande(demandeDTO)).willReturn(demande);
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(EMPLACEMENT_DEMANDE_AJOUTE, reponse.getLocation().toString());
	}
	
	@Test
	public void lorsqueAjouterUneDemandeAvecCourrielInvalideDevraitRetrouverStatusBAD_REQUEST(){
		given(reservationDemandeAssembleur.assemblerDemande(demandeDTO)).willThrow(COURRIEL_INVALIDE_EXCEPTION);
		Response reponse = ressource.ajouterDemande(demandeDTO);
		assertEquals(Status.BAD_REQUEST.getStatusCode(), reponse.getStatus());
	}
}
