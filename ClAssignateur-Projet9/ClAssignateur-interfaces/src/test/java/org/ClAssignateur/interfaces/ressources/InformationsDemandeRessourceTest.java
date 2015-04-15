package org.ClAssignateur.interfaces.ressources;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.interfaces.ressources.InformationsDemandeRessource;

import javax.ws.rs.core.Response.Status;

import org.ClAssignateur.services.infosDemandes.DemandeIntrouvableException;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTOAssembleur;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTOAssembleur;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class InformationsDemandeRessourceTest {

	private final String ADRESSE_COURRIEL_ORGANISATEUR = "courriel@domain.com";
	private final String NUMERO_DEMANDE = UUID.randomUUID().toString();

	private List<Demande> demandes;
	private ServiceInformationsDemande service;
	private InformationsDemandeDTOAssembleur infosDemandesAssembleur;
	private OrganisateurDemandesDTOAssembleur organisateurDemandesAssembleur;
	private InformationsDemandeDTO dto;
	private Demande demande;

	private InformationsDemandeRessource ressource;

	@Before
	public void initialement() {
		service = mock(ServiceInformationsDemande.class);
		infosDemandesAssembleur = mock(InformationsDemandeDTOAssembleur.class);
		organisateurDemandesAssembleur = mock(OrganisateurDemandesDTOAssembleur.class);
		demande = mock(Demande.class);
		dto = mock(InformationsDemandeDTO.class);
		demandes = new ArrayList<Demande>();
		given(service.getDemandesPourCourriel(ADRESSE_COURRIEL_ORGANISATEUR)).willReturn(demandes);

		ressource = new InformationsDemandeRessource(service, infosDemandesAssembleur, organisateurDemandesAssembleur);
	}

	@Test
	public void lorsqueAfficherDemandeDevraitRetrouverLesInfosSelonLesBonCourrielEtId() {
		ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		verify(service).getInfoDemandePourCourrielEtId(eq(ADRESSE_COURRIEL_ORGANISATEUR),
				eq(UUID.fromString(NUMERO_DEMANDE)));
	}

	@Test
	public void etantDonneDemandeExisteAlorsConstruitLeDTO() {
		faireEnSorteQueDemandeExiste();
		ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		verify(infosDemandesAssembleur).assemblerInformationsDemandeDTO(demande);
	}

	@Test
	public void etantDonneDemandeExisteAlorsStatusDevraitEtreOk() {
		faireEnSorteQueDemandeExiste();
		Response reponse = ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void etantDonneDemandeExisteAlorsReponseDevraitAvoirBonDTO() {
		faireEnSorteQueDemandeExiste();
		Response reponse = ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(dto, reponse.getEntity());
	}

	private void faireEnSorteQueDemandeExiste() {
		given(service.getInfoDemandePourCourrielEtId(ADRESSE_COURRIEL_ORGANISATEUR, UUID.fromString(NUMERO_DEMANDE)))
				.willReturn(demande);
		given(infosDemandesAssembleur.assemblerInformationsDemandeDTO(demande)).willReturn(dto);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void etantDonneDemandeNExistePasAlorsStatusDevraitEtre404() {
		given(service.getInfoDemandePourCourrielEtId(anyString(), any(UUID.class))).willThrow(
				DemandeIntrouvableException.class);
		Response reponse = ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.NOT_FOUND.getStatusCode(), reponse.getStatus());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void etantDonneErreurQuelconqueAlorsStatusDevraitEtre500() {
		given(service.getInfoDemandePourCourrielEtId(anyString(), any(UUID.class))).willThrow(Exception.class);
		Response reponse = ressource.afficherDemande(ADRESSE_COURRIEL_ORGANISATEUR, NUMERO_DEMANDE);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void lorsqueAfficherDemandesPourCourrielDevraitRecupererLesInfos() {
		ressource.afficherDemandesPourOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		verify(service).getDemandesPourCourriel(ADRESSE_COURRIEL_ORGANISATEUR);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void lorsqueAfficherDemandesPourCourrielSiErreurAlorsStatusDevraitEtre500() {
		given(service.getDemandesPourCourriel(ADRESSE_COURRIEL_ORGANISATEUR)).willThrow(Exception.class);
		Response reponse = ressource.afficherDemandesPourOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		assertEquals(Status.INTERNAL_SERVER_ERROR.getStatusCode(), reponse.getStatus());
	}

	@Test
	public void lorsqueAfficherDemandesPourCourrielLorsqueRecoitListeDemandesDevraitConstruireDTO() {
		ressource.afficherDemandesPourOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		verify(organisateurDemandesAssembleur).assemblerOrganisateurDemandesDTO(demandes);
	}

	@Test
	public void lorsqueAfficherDemandesPourCourrielLorsqueRecoitListeDemandesDevraitEtreOk() {
		Response reponse = ressource.afficherDemandesPourOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		assertEquals(Status.OK.getStatusCode(), reponse.getStatus());
	}
}
