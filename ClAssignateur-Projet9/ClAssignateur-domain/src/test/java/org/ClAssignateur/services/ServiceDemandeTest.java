package org.ClAssignateur.services;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import org.ClAssignateur.interfaces.DemandeResultatAssembleur;

import java.util.UUID;
import java.util.Optional;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import org.junit.Test;
import org.junit.Before;

public class ServiceDemandeTest {

	private final String COURRIEL_ORGANISATEUR = "courriel@gmail.com";
	private final UUID UUID_DEMANDE = UUID.randomUUID();

	private DemandesEntrepot demandesEntrepot;
	private ServiceDemande serviceDemande;
	private Demande demande;
	private Optional<Demande> demandeOptional;
	private DemandeResultatAssembleur demandeAssembleur;

	@Before
	public void Initialisation() {
		demandesEntrepot = mock(DemandesEntrepot.class);
		demandeAssembleur = mock(DemandeResultatAssembleur.class);
		demande = mock(Demande.class);
		demandeOptional = Optional.of(demande);
		serviceDemande = new ServiceDemande(demandesEntrepot, demandeAssembleur);

		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(demandeOptional);
	}

	@Test
	public void quandGetInfoDemandePourCourrielEtIdVaChercherLaDemandeDansEntrepot() {
		serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		verify(demandesEntrepot).obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test
	public void quandGetInfoDemandePourCourrielEtIdCreeDTOAvecDemandeResultatAssembleur() {
		serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		verify(demandeAssembleur).assemblerDemande(demande);
	}

	@Test(expected = DemandePasPresenteException.class)
	public void quandGetInforDemandePourCourrielEtIdSiDemandePasDansEntrepotLanceException() {
		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(Optional.empty());
		serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}
}
