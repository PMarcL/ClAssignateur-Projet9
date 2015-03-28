package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import org.ClAssignateur.services.DemandePasPresenteException;
import javax.ws.rs.core.Response.Status;
import org.ClAssignateur.services.ServiceDemande;
import javax.ws.rs.core.Response;
import org.junit.Before;
import java.util.UUID;
import org.junit.Test;

public class DemandeRessourceTest {

	private static final String COURRIEL_ORGANISATEUR = "courriel";
	private static final String NUMERO_DEMANDE = UUID.randomUUID().toString();

	private ServiceDemande service;

	private DemandeRessource ressource;

	@Before
	public void initialement() {
		service = mock(ServiceDemande.class);
		ressource = new DemandeRessource(service);
	}

	@Test
	public void lorsqueAfficherDemandeDevraitRetrouverLesInfosSelonLesBonCourrielEtId() {
		ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		verify(service).getInfoDemandePourCourrielEtId(eq(COURRIEL_ORGANISATEUR), eq(UUID.fromString(NUMERO_DEMANDE)));
	}

	@Test
	public void etantDonneDemandeExisteAlorsStatusDevraitEtreOk() {
		faireEnSorteQueDemandeExiste();
		Response reponse = ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void etantDonneDemandeExisteAlorsReponseDevraitAvoirBonDTO() {
		DemandeDTO dto = faireEnSorteQueDemandeExiste();
		Response reponse = ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(dto, reponse.getEntity());
	}

	private DemandeDTO faireEnSorteQueDemandeExiste() {
		DemandeDTO dto = new DemandeDTO();
		given(service.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID.fromString(NUMERO_DEMANDE)))
				.willReturn(dto);
		return dto;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void etantDonneDemandeNExistePasAlorsStatusDevraitEtre404() {
		given(service.getInfoDemandePourCourrielEtId(anyString(), any(UUID.class))).willThrow(
				DemandePasPresenteException.class);
		Response reponse = ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.NOT_FOUND.getStatusCode(), reponse.getStatus());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void etantDonneErreurQuelconqueAlorsStatusDevraitEtre500() {
		given(service.getInfoDemandePourCourrielEtId(anyString(), any(UUID.class))).willThrow(Exception.class);
		Response reponse = ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), reponse.getStatus());
	}
}
