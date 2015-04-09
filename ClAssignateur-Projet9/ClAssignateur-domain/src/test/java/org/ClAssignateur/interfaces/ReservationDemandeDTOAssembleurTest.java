package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.demandes.Demande;

public class ReservationDemandeDTOAssembleurTest {

	private final int NB_PERSONNES = 5;
	private final String COURRIEL_ORGANISATEUR = "courrielOrganisateur@courriel.com";
	private final String COURRIEL_PARTICIPANT = "courrielParticipant@couriel.com";
	private final int NIVEAU_PRIORITE = 3;

	private ReservationDemandeDTOAssembleur assembleur;
	private ReservationDemandeDTO demandeDTO;

	@Before
	public void initialisation() {
		configurerDTO();
		assembleur = new ReservationDemandeDTOAssembleur();
	}

	@Test
	public void etantDonneUneDemandeDTOAvecUnNombreDePersonnesXQuandAssembleDemandeAlorsRetourneDemandeAvecNombreDeParticipantsX() {
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertEquals(NB_PERSONNES, demandeResultat.getNbParticipants());
	}

	@Test
	public void etantDonneUneDemandeDTOAvecCourrielOrganisateurXQuandAssembleDemandeAlorsRetourneDemandeAvecCourrielOrganisateurX() {
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertEquals(COURRIEL_ORGANISATEUR, demandeResultat.getOrganisateur().courriel);
	}

	@Test
	public void etantDonneUneDemandeDTOAvecCourrielOrganisateurXQuandAssembleDemandeAlorsRetourneDemandeAvecCourrielResponsableX() {
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertEquals(COURRIEL_ORGANISATEUR, demandeResultat.getResponsable().courriel);
	}

	@Test
	public void etantDonneUneDemandeDTOAvecPrioriteXQuandAssembleDemandeAlorsRetourneDemandeAvecPrioriteX() {
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertEquals(NIVEAU_PRIORITE, demandeResultat.getNiveauPriorite());
	}

	@Test
	public void etantDonneUneDemandeDTOLaListeDeCourrielDeParticipantsQuandAssembleDemandeAlorsRetourneDemandeAvecDesParticipantsAvecLesCourriels() {
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		List<Object> listeCourrielParticipants = getListeCourrielParticipants(demandeResultat);
		assertTrue(listeCourrielParticipants.containsAll(listeCourrielParticipants()));
	}

	// TODO manque probablement des cas de tests (ex : 5 participants, mais
	// seulement 4 adresses courriels fournies)

	private void configurerDTO() {
		demandeDTO = new ReservationDemandeDTO();
		demandeDTO.nombrePersonnes = NB_PERSONNES;
		demandeDTO.courrielOrganisateur = COURRIEL_ORGANISATEUR;
		demandeDTO.priorite = NIVEAU_PRIORITE;
		demandeDTO.participantsCourriels = listeCourrielParticipants();
	}

	private List<String> listeCourrielParticipants() {
		ArrayList<String> courrielsParticipants = new ArrayList<String>();
		for (int i = 0; i < NB_PERSONNES; i++)
			courrielsParticipants.add(COURRIEL_PARTICIPANT);

		return courrielsParticipants;
	}

	private List<Object> getListeCourrielParticipants(Demande demandeResultat) {
		List<Employe> participants = demandeResultat.getParticipants();
		Object[] courrielParticipants = participants.stream().map(p -> p.courriel).toArray();
		List<Object> listeCourrielParticipants = java.util.Arrays.asList(courrielParticipants);
		return listeCourrielParticipants;
	}
}
