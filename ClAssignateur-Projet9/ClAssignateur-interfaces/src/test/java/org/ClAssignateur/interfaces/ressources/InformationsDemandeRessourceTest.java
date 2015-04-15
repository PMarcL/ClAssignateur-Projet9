package org.ClAssignateur.interfaces.ressources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.interfaces.ressources.InformationsDemandeRessource;

import javax.ws.rs.core.Response.Status;

import org.ClAssignateur.services.infosDemandes.DemandeIntrouvableException;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTO;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import java.util.UUID;

public class InformationsDemandeRessourceTest {

	private final String ADRESSE_COURRIEL_ORGANISATEUR = "courriel@domain.com";
	private final String NUMERO_DEMANDE = UUID.randomUUID().toString();

	private ServiceInformationsDemande service;
	private InformationsDemandeDTO informationsDemande;
	private OrganisateurDemandesDTO demandesOrganisateur;

	private InformationsDemandeRessource ressource;

	@Before
	public void initialement() {
		service = mock(ServiceInformationsDemande.class);
		informationsDemande = mock(InformationsDemandeDTO.class);
		demandesOrganisateur = mock(OrganisateurDemandesDTO.class);
		given(service.getInfoDemandePourCourrielEtId(ADRESSE_COURRIEL_ORGANISATEUR, UUID.fromString(NUMERO_DEMANDE)))
				.willReturn(informationsDemande);
		given(service.getDemandesPourCourriel(ADRESSE_COURRIEL_ORGANISATEUR)).willReturn(demandesOrganisateur);

		ressource = new InformationsDemandeRessource(service);
	}

	@Test
	public void quandAfficherDemandeDevraitRetrouverInfosSelonCourrielEtId() {
		ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		verify(service).getInfoDemandePourCourrielEtId(eq(ADRESSE_COURRIEL_ORGANISATEUR),
				eq(UUID.fromString(NUMERO_DEMANDE)));
	}

	@Test
	public void etantDonneDemandeExisteQuandAfficherDemandeAlorsStatutDevraitEtreOk() {
		Response reponse = ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void etantDonneDemandeExisteQuandAfficherDemandeAlorsReponseDevraitAvoirBonDTO() {
		Response reponse = ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(informationsDemande, reponse.getEntity());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void etantDonneDemandeNExistePasQuandAfficherDemandeAlorsStatutDevraitEtre404() {
		given(service.getInfoDemandePourCourrielEtId(anyString(), any(UUID.class))).willThrow(
				DemandeIntrouvableException.class);
		Response reponse = ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.NOT_FOUND.getStatusCode(), reponse.getStatus());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void etantDonneErreurQuelconqueQuandAfficherDemandeAlorsStatutDevraitEtre500() {
		given(service.getInfoDemandePourCourrielEtId(anyString(), any(UUID.class))).willThrow(Exception.class);
		Response reponse = ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void quandAfficherDemandesOrganisateurDevraitRecupererResultatDuService() {
		ressource.afficherDemandesOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		verify(service).getDemandesPourCourriel(ADRESSE_COURRIEL_ORGANISATEUR);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void etantDonneErreurQuelconqueQuandAfficherDemandesOrganisateurAlorsStatutDevraitEtre500() {
		given(service.getDemandesPourCourriel(ADRESSE_COURRIEL_ORGANISATEUR)).willThrow(Exception.class);
		Response reponse = ressource.afficherDemandesOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void etantDonneSuccesTraitementQuandAfficherDemandesOrganisateurAlorsStatutDevraitEtreOk() {
		Response reponse = ressource.afficherDemandesOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void etantDonneSuccesTraitementQuandAfficherDemandesOrganisateurAlorsReponseDevraitAvoirBonDTO() {
		Response reponse = ressource.afficherDemandesOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		assertEquals(demandesOrganisateur, reponse.getEntity());
	}
}
