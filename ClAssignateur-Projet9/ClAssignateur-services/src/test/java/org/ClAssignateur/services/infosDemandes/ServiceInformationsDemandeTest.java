package org.ClAssignateur.services.infosDemandes;

import static org.mockito.BDDMockito.*;

import org.junit.Test;
import org.junit.Before;

import java.util.UUID;
import java.util.Optional;

import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import org.ClAssignateur.services.infosDemandes.DemandePasPresenteException;
import org.ClAssignateur.services.infosDemandes.ServiceInformationsDemande;

public class ServiceInformationsDemandeTest {

	private final UUID UUID_DEMANDE = UUID.randomUUID();
	private final String ADRESSE_COURRIEL_ORGANISATEUR = "courriel@domaine.com";

	private DemandesEntrepot demandesEntrepot;
	private ServiceInformationsDemande serviceDemande;
	private Demande demande;
	private Optional<Demande> demandeOptional;

	@Before
	public void Initialisation() {
		demandesEntrepot = mock(DemandesEntrepot.class);
		demande = mock(Demande.class);
		demandeOptional = Optional.of(demande);
		serviceDemande = new ServiceInformationsDemande(demandesEntrepot);

		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(demandeOptional);
	}

	@Test
	public void quandGetInfoDemandePourCourrielEtIdVaChercherLaDemandeDansEntrepot() {
		serviceDemande.getInfoDemandePourCourrielEtId(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		verify(demandesEntrepot).obtenirDemandeSelonCourrielOrganisateurEtId(ADRESSE_COURRIEL_ORGANISATEUR,
				UUID_DEMANDE);
	}

	@Test(expected = DemandePasPresenteException.class)
	public void quandGetInfoDemandePourCourrielEtIdSiDemandePasDansEntrepotLanceException() {
		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(Optional.empty());
		serviceDemande.getInfoDemandePourCourrielEtId(ADRESSE_COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test
	public void quandGetDemandesPourCourrielVaChercherDansEntrepot() {
		serviceDemande.getDemandesPourCourriel(ADRESSE_COURRIEL_ORGANISATEUR);
		verify(demandesEntrepot).obtenirDemandesSelonCourriel(ADRESSE_COURRIEL_ORGANISATEUR);
	}

}
