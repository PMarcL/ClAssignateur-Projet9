package org.ClAssignateur.services.reservations.dto;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.groupe.InformationsContact;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;

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
		String adresseCourrielOrganisateur = demandeResultat.getCourrielOrganisateur();
		assertTrue(adresseCourrielOrganisateur.equals(COURRIEL_ORGANISATEUR));
	}

	@Test
	public void etantDonneUneDemandeDTOAvecCourrielOrganisateurXQuandAssembleDemandeAlorsRetourneDemandeAvecCourrielResponsableX() {
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		String adresseCourrielResponsable = demandeResultat.getResponsable().getAdresseCourriel();
		assertTrue(adresseCourrielResponsable.equals(COURRIEL_ORGANISATEUR));
	}

	@Test
	public void etantDonneUneDemandeDTOAvecPrioriteXQuandAssembleDemandeAlorsRetourneDemandeAvecPrioriteX() {
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertEquals(NIVEAU_PRIORITE, demandeResultat.getNiveauPriorite());
	}

	@Test
	public void etantDonneUneDemandeDTOLaListeDeCourrielDeParticipantsQuandAssembleDemandeAlorsRetourneDemandeAvecDesParticipantsAvecLesCourriels() {
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		List<Object> listeCourrielParticipantsResultat = getListeCourrielParticipants(demandeResultat);
		assertTrue(listeCourrielParticipantsResultat.containsAll(listeCourrielParticipants(NB_PERSONNES)));
	}

	private void configurerDTO() {
		demandeDTO = new ReservationDemandeDTO();
		demandeDTO.nombrePersonnes = NB_PERSONNES;
		demandeDTO.courrielOrganisateur = COURRIEL_ORGANISATEUR;
		demandeDTO.priorite = NIVEAU_PRIORITE;
		demandeDTO.participantsCourriels = listeCourrielParticipants(NB_PERSONNES);
	}

	private List<String> listeCourrielParticipants(int longueurListe) {
		ArrayList<String> courrielsParticipants = new ArrayList<String>();
		for (int i = 0; i < longueurListe; i++)
			courrielsParticipants.add(i + COURRIEL_PARTICIPANT);

		return courrielsParticipants;
	}

	private List<Object> getListeCourrielParticipants(Demande demandeResultat) {
		List<InformationsContact> participants = demandeResultat.getParticipants();
		Object[] courrielParticipants = participants.stream().map(p -> p.getAdresseCourriel()).toArray();
		List<Object> listeCourrielParticipants = java.util.Arrays.asList(courrielParticipants);
		return listeCourrielParticipants;
	}
}
