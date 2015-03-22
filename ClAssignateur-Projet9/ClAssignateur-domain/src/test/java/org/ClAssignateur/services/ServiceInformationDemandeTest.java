package org.ClAssignateur.services;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;
import java.util.Optional;
import org.ClAssignateur.domain.demandes.Demande;
import org.ClAssignateur.domain.demandes.DemandesEntrepot;
import org.junit.Test;
import org.junit.Before;

public class ServiceInformationDemandeTest {

	private final String COURRIEL_ORGANISATEUR = "courriel@gmail.com";
	private final int NB_PARTICIPANTS = 20;

	private DemandesEntrepot demandesEntrepot;
	private ServiceInformationDemande serviceInfoDemande;
	private Demande demande;
	private Optional<Demande> demandeOptional;

	@Before
	public void Initialisation() {
		demandesEntrepot = mock(DemandesEntrepot.class);
		demande = mock(Demande.class);
		demandeOptional = Optional.of(demande);
		serviceInfoDemande = new ServiceInformationDemande(demandesEntrepot);

		given(demande.getNbParticipants()).willReturn(NB_PARTICIPANTS);
		given(demandesEntrepot.obtenirDemandeSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR)).willReturn(
				demandeOptional);
	}

	@Test
	public void etantDonneUneDemandeDansEntrepotQuandGetNbParticipantsSelonCourrielOrganisateurDevraitAllerChercherDemandeDansEntrepot() {
		serviceInfoDemande.getNbParticipantsSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
		verify(demandesEntrepot).obtenirDemandeSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);
	}

	@Test
	public void etantDonneUneDemandeDansEntrepotQuandGetNbParticipantsSelonCourrielOrganisateurRetourneLeBonNombreDeParticipant() {
		int resultat = serviceInfoDemande.getNbParticipantsSelonCourrielOrganisateur(COURRIEL_ORGANISATEUR);

		assertEquals(NB_PARTICIPANTS, resultat);
	}

}
