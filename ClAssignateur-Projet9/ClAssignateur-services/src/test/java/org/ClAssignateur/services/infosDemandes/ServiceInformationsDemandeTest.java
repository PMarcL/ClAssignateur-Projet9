package org.ClAssignateur.services.infosDemandes;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Test;
import org.junit.Before;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.services.infosDemandes.DemandeIntrouvableException;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTOAssembleur;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTO;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTOAssembleur;

public class ServiceInformationsDemandeTest {

	private final UUID UUID_DEMANDE = UUID.randomUUID();
	private final String ADRESSE_COURRIEL_ORGANISATEUR = "courriel@domaine.com";

	private ConteneurDemandes conteneurDemandes;
	private ServiceInformationsDemande serviceDemande;
	private Demande demande;
	private Optional<Demande> demandeOptional;
	private InformationsDemandeDTOAssembleur infosDemandeAssembleur;
	private InformationsDemandeDTO infosDemande;
	private OrganisateurDemandesDTOAssembleur organisateurDemandesAssembleur;
	private OrganisateurDemandesDTO organisateurDemandes;

	@Before
	public void Initialisation() {
		configurerMocks();
		serviceDemande = new ServiceInformationsDemande(conteneurDemandes, infosDemandeAssembleur,
				organisateurDemandesAssembleur);
	}

	@Test
	public void quandGetInformationsDemandeDevraitChercherDemandeDansConteneurDemandes() {
		serviceDemande.getInformationsDemande(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		verify(conteneurDemandes).obtenirDemandeSelonId(UUID_DEMANDE);
	}

	@Test
	public void etantDonneDemandeTrouveConcerneOrganisateurQuandGetInformationsDemandeDevraitRetournerInformationsDemande() {
		InformationsDemandeDTO infosDemandeResultat = serviceDemande.getInformationsDemande(
				ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		assertEquals(infosDemande, infosDemandeResultat);
	}

	@Test(expected = DemandeIntrouvableException.class)
	public void etantDonneDemandeTrouveConvernePasOrganisateurquandGetInformationsDemandeDevraitLancerException() {
		final String AUTRE_ADRESSE_COURRIEL = "CeciEstUneAutreAdresseCourriel";
		given(demande.getCourrielOrganisateur()).willReturn(AUTRE_ADRESSE_COURRIEL);
		serviceDemande.getInformationsDemande(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test(expected = DemandeIntrouvableException.class)
	public void etanDonneDemandePasDansConteneurDemandesQuandGetInformationsDemandeDevraitLancerException() {
		given(conteneurDemandes.obtenirDemandeSelonId(UUID_DEMANDE)).willReturn(Optional.empty());
		serviceDemande.getInformationsDemande(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test
	public void quandGetDemandesOrganisateurDevraitChercherDansEntrepot() {
		serviceDemande.getDemandesOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		verify(conteneurDemandes).obtenirDemandesSelonCourrielOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
	}

	@Test
	public void quandGetDemandesOrganisateurDevraitRetournerDemandesOrganisateur() {
		OrganisateurDemandesDTO organisateurDemandesResultat = serviceDemande
				.getDemandesOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		assertEquals(organisateurDemandes, organisateurDemandesResultat);
	}

	@SuppressWarnings("unchecked")
	private void configurerMocks() {
		conteneurDemandes = mock(ConteneurDemandes.class);
		demande = mock(Demande.class);
		demandeOptional = Optional.of(demande);
		infosDemandeAssembleur = mock(InformationsDemandeDTOAssembleur.class);
		infosDemande = mock(InformationsDemandeDTO.class);
		organisateurDemandesAssembleur = mock(OrganisateurDemandesDTOAssembleur.class);
		organisateurDemandes = mock(OrganisateurDemandesDTO.class);

		given(demande.getCourrielOrganisateur()).willReturn(ADRESSE_COURRIEL_ORGANISATEUR);
		given(conteneurDemandes.obtenirDemandeSelonId(UUID_DEMANDE)).willReturn(demandeOptional);
		given(infosDemandeAssembleur.assemblerInformationsDemandeDTO(demande)).willReturn(infosDemande);
		given(organisateurDemandesAssembleur.assemblerOrganisateurDemandesDTO(any(List.class))).willReturn(
				organisateurDemandes);
	}
}
