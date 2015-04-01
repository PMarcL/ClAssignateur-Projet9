package org.ClAssignateur.services;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import java.util.UUID;
import java.util.Optional;
import org.ClAssignateur.interfaces.DemandeDTO;
import org.ClAssignateur.interfaces.DemandeDTOAssembleur;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class ServiceDemandeTest {

	private final String COURRIEL_ORGANISATEUR = "courriel@gmail.com";
	private final UUID UUID_DEMANDE = UUID.randomUUID();

	private DemandesEntrepot demandesEntrepot;
	private ServiceDemande serviceDemande;
	private Demande demande;
	private Optional<Demande> demandeOptional;
	private DemandeDTOAssembleur demandeAssembleur;
	private DemandeDTO demandeDTO;

	@Before
	public void Initialisation() {
		demandesEntrepot = mock(DemandesEntrepot.class);
		demandeAssembleur = mock(DemandeDTOAssembleur.class);
		demandeDTO = mock(DemandeDTO.class);
		demande = mock(Demande.class);
		demandeOptional = Optional.of(demande);
		serviceDemande = new ServiceDemande(demandesEntrepot, demandeAssembleur);

		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(demandeOptional);
		given(demandeAssembleur.assemblerDemandeDTO(demande)).willReturn(demandeDTO);
	}

	@Test
	public void quandGetInfoDemandePourCourrielEtIdVaChercherLaDemandeDansEntrepot() {
		serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		verify(demandesEntrepot).obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test
	public void quandGetInfoDemandePourCourrielEtIdCreeDTOAvecDemandeResultatAssembleur() {
		serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		verify(demandeAssembleur).assemblerDemandeDTO(demande);
	}

	@Test(expected = DemandePasPresenteException.class)
	public void quandGetInfoDemandePourCourrielEtIdSiDemandePasDansEntrepotLanceException() {
		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(Optional.empty());
		serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test
	public void quandGetInfoDemandePourCourrielEtIdRetourneDemandeResultatAssembler() {
		DemandeDTO demandeDTORecu = serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR,
				UUID_DEMANDE);
		assertEquals(demandeDTO, demandeDTORecu);
	}
}
