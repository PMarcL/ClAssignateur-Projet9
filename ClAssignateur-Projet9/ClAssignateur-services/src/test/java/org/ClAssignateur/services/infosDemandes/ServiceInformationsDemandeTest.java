package org.ClAssignateur.services.infosDemandes;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import org.junit.Test;
import org.junit.Before;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.demandes.DemandesEntrepot;
import org.ClAssignateur.services.infosDemandes.DemandeIntrouvableException;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTO;
import org.ClAssignateur.services.infosDemandes.dto.InformationsDemandeDTOAssembleur;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTO;
import org.ClAssignateur.services.infosDemandes.dto.OrganisateurDemandesDTOAssembleur;

public class ServiceInformationsDemandeTest {

	private final UUID UUID_DEMANDE = UUID.randomUUID();
	private final String ADRESSE_COURRIEL_ORGANISATEUR = "courriel@domaine.com";

	private DemandesEntrepot demandesEntrepot;
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
		serviceDemande = new ServiceInformationsDemande(demandesEntrepot, infosDemandeAssembleur,
				organisateurDemandesAssembleur);
	}

	@Test
	public void quandGetInformationsDemandeDevraitChercherDemandeDansEntrepot() {
		serviceDemande.getInformationsDemande(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		verify(demandesEntrepot).obtenirDemandeSelonCourrielOrganisateurEtId(ADRESSE_COURRIEL_ORGANISATEUR,
				UUID_DEMANDE);
	}

	@Test
	public void quandGetInformationsDemandeDevraitRetournerInformationsDemande() {
		InformationsDemandeDTO infosDemandeResultat = serviceDemande.getInformationsDemande(
				ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		assertEquals(infosDemande, infosDemandeResultat);
	}

	@Test(expected = DemandeIntrouvableException.class)
	public void etanDonneDemandePasDansEntrepotQuandGetInformationsDemandeDevraitLancerException() {
		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(Optional.empty());
		serviceDemande.getInformationsDemande(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test
	public void quandGetDemandesOrganisateurDevraitChercherDansEntrepot() {
		serviceDemande.getDemandesOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		verify(demandesEntrepot).obtenirDemandesSelonCourriel(ADRESSE_COURRIEL_ORGANISATEUR);
	}

	@Test
	public void quandGetDemandesOrganisateurDevraitRetournerDemandesOrganisateur() {
		OrganisateurDemandesDTO organisateurDemandesResultat = serviceDemande
				.getDemandesOrganisateur(ADRESSE_COURRIEL_ORGANISATEUR);
		assertEquals(organisateurDemandes, organisateurDemandesResultat);
	}

	@SuppressWarnings("unchecked")
	private void configurerMocks() {
		demandesEntrepot = mock(DemandesEntrepot.class);
		demande = mock(Demande.class);
		demandeOptional = Optional.of(demande);
		infosDemandeAssembleur = mock(InformationsDemandeDTOAssembleur.class);
		infosDemande = mock(InformationsDemandeDTO.class);
		organisateurDemandesAssembleur = mock(OrganisateurDemandesDTOAssembleur.class);
		organisateurDemandes = mock(OrganisateurDemandesDTO.class);

		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(demandeOptional);
		given(infosDemandeAssembleur.assemblerInformationsDemandeDTO(demande)).willReturn(infosDemande);
		given(organisateurDemandesAssembleur.assemblerOrganisateurDemandesDTO(any(List.class))).willReturn(
				organisateurDemandes);
	}
}
