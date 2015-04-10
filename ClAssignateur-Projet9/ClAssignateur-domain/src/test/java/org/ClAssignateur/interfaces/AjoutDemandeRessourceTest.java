package org.ClAssignateur.interfaces;

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
	private final String EMPLACEMENT_DEMANDE_AJOUTE = "/demandes/" + COURRIEL_ORGANISATEUR + "/" + DEMANDE_ID.toString();
	
	private ReservationDemandeDTO demandeDTOValide;
	private ServiceDemande service;
	private ReservationDemandeDTOAssembleur reservationDemandeAssembleur;
	private Demande demandeValide;
	
	private AjoutDemandeRessource ressource;
	
	@Before
	public void initialement() {
		service = mock(ServiceDemande.class);
		reservationDemandeAssembleur = mock(ReservationDemandeDTOAssembleur.class);
		demandeValide = mock(Demande.class);
		demandeDTOValide = new ReservationDemandeDTO();
		demandeDTOValide.courrielOrganisateur = COURRIEL_ORGANISATEUR;
		given(demandeValide.getID()).willReturn(DEMANDE_ID);
		given(reservationDemandeAssembleur.assemblerDemande(demandeDTOValide)).willReturn(demandeValide);
		
		ressource = new AjoutDemandeRessource(service, reservationDemandeAssembleur);
	}
	
	@Test
	public void lorsqueAjouterUneDemandeValideDevraitRetrouverStatusCREATED(){
		Response reponse = ressource.ajouterDemande(demandeDTOValide);
		assertEquals(Status.CREATED.getStatusCode(), reponse.getStatus());
	}
	
	@Test
	public void lorsqueAjouterUneDemandeValideDevraitRetrouverLeBonEmplacementDeLaDemandeAjoute(){
		Response reponse = ressource.ajouterDemande(demandeDTOValide);
		assertEquals(EMPLACEMENT_DEMANDE_AJOUTE, reponse.getLocation().toString());
	}
}
