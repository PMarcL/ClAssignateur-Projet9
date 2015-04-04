package org.ClAssignateur.services;

import static org.mockito.BDDMockito.*;
import org.junit.Test;
import org.junit.Before;
import java.util.UUID;
import java.util.Optional;
import org.ClAssignateur.interfaces.InformationsDemandeDTO;
import org.ClAssignateur.interfaces.InformationsDemandeDTOAssembleur;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;

public class ServiceDemandeTest {

	private final String COURRIEL_ORGANISATEUR = "courriel@gmail.com";
	private final UUID UUID_DEMANDE = UUID.randomUUID();

	private DemandesEntrepot demandesEntrepot;
	private ServiceDemande serviceDemande;
	private Demande demande;
	private Optional<Demande> demandeOptional;
	private InformationsDemandeDTOAssembleur demandeAssembleur;
	private InformationsDemandeDTO demandeDTO;

	@Before
	public void Initialisation() {
		demandesEntrepot = mock(DemandesEntrepot.class);
		demandeAssembleur = mock(InformationsDemandeDTOAssembleur.class);
		demandeDTO = mock(InformationsDemandeDTO.class);
		demande = mock(Demande.class);
		demandeOptional = Optional.of(demande);
		serviceDemande = new ServiceDemande(demandesEntrepot);

		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(demandeOptional);
		given(demandeAssembleur.assemblerInformationsDemandeDTO(demande)).willReturn(demandeDTO);
	}

	@Test
	public void quandGetInfoDemandePourCourrielEtIdVaChercherLaDemandeDansEntrepot() {
		serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
		verify(demandesEntrepot).obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test(expected = DemandePasPresenteException.class)
	public void quandGetInfoDemandePourCourrielEtIdSiDemandePasDansEntrepotLanceException() {
		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateurEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE))
				.willReturn(Optional.empty());
		serviceDemande.getInfoDemandePourCourrielEtId(COURRIEL_ORGANISATEUR, UUID_DEMANDE);
	}

	@Test
	public void quandGetDemandesPourCourrielVaChercherDansEntrepot() {
		serviceDemande.getDemandesPourCourriel(COURRIEL_ORGANISATEUR);
		verify(demandesEntrepot).obtenirDemandesSelonCourriel(COURRIEL_ORGANISATEUR);
	}

}
