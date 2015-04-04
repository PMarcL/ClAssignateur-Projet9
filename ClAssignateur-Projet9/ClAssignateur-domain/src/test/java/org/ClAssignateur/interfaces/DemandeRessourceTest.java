package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.services.DemandePasPresenteException;
import javax.ws.rs.core.Response.Status;
import org.ClAssignateur.services.ServiceDemande;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class DemandeRessourceTest {

	private static final String COURRIEL_ORGANISATEUR = "courriel";
	private static final String NUMERO_DEMANDE = UUID.randomUUID().toString();

	private List<Demande> demandes;
	private ServiceDemande service;
	private DemandeDTOAssembleur assembleur;
	private DemandeDTO dto;
	private DemandesOrganisateurDTO demandesCourrielDTO;
	private Demande demande;

	private DemandeRessource ressource;

	@Before
	public void initialement() {
		service = mock(ServiceDemande.class);
		assembleur = mock(DemandeDTOAssembleur.class);
		demande = mock(Demande.class);
		demandesCourrielDTO = mock(DemandesOrganisateurDTO.class);
		dto = mock(DemandeDTO.class);
		demandes = new ArrayList<Demande>();
		given(service.getDemandesPourCourriel(COURRIEL_ORGANISATEUR)).willReturn(demandes);

		ressource = new DemandeRessource(service, assembleur);
	}

	@Test
	public void lorsqueAfficherDemandeDevraitRetrouverLesInfosSelonLesBonCourrielEtId() {
		ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		verify(service).getInfoDemandePourCourrielEtId(eq(COURRIEL_ORGANISATEUR), eq(UUID.fromString(NUMERO_DEMANDE)));
	}

	@Test
	public void etantDonneDemandeExisteAlorsConstruitLeDTO() {
		faireEnSorteQueDemandeExiste();
		ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		verify(assembleur).assemblerDemandeDTO(demande);
	}

	@Test
	public void etantDonneDemandeExisteAlorsStatusDevraitEtreOk() {
		faireEnSorteQueDemandeExiste();
		Response reponse = ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void etantDonneDemandeExisteAlorsReponseDevraitAvoirBonDTO() {
		faireEnSorteQueDemandeExiste();
		Response reponse = ressource.afficherDemande(COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(dto, reponse.getEntity());
	}

	private void faireEnSorteQueDemandeExiste() {
		given(service.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID.fromString(NUMERO_DEMANDE)))
				.willReturn(demande);
		given(assembleur.assemblerDemandeDTO(demande)).willReturn(dto);
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

	@Test
	public void lorsqueAfficherDemandesPourCourrielDevraitRecupererLesInfos() {
		ressource.afficherDemandesPourCourriel(COURRIEL_ORGANISATEUR);
		verify(service).getDemandesPourCourriel(COURRIEL_ORGANISATEUR);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void lorsqueAfficherDemandesPourCourrielSiErreurAlorsStatusDevraitEtre500() {
		given(service.getDemandesPourCourriel(COURRIEL_ORGANISATEUR)).willThrow(Exception.class);
		Response reponse = ressource.afficherDemandesPourCourriel(COURRIEL_ORGANISATEUR);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void lorsqueAfficherDemandesPourCourrielLorsqueRecoitListeDemandesDevraitConstruireDTO() {
		ressource.afficherDemandesPourCourriel(COURRIEL_ORGANISATEUR);
		verify(assembleur).assemblerDemandesPourCourrielDTO(demandes);
	}

	@Test
	public void lorsqueAfficherDemandesPourCourrielLorsqueRecoitListeDemandesDevraitEtreOk() {
		Response reponse = ressource.afficherDemandesPourCourriel(COURRIEL_ORGANISATEUR);
		assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
	}
}
