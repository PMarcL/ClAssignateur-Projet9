package org.ClAssignateur.interfaces;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.ClAssignateur.domain.demandes.Priorite;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import org.ClAssignateur.domain.demandes.Demande.StatutDemande;
import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.groupe.Employe;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.demandes.Demande;

public class ReservationDemandeDTOAssembleurTest {

	private final int NB_PERSONNES = 5;
	private final String COURRIEL_ORGANISATEUR = "courrielOrganisateur@courriel.com";
	private final int PRIORITE_MOYENNE = 3 ;
	private final List<String> COURRIEL_PARTICIPANTS = Collections.unmodifiableList(
		    new ArrayList<String>() {
				private static final long serialVersionUID = 1L;
			{
		        add("courriel1@courriel.com");
		        add("courriel2@courriel.com");
		        add("courriel3@courriel.com");
		    }});

	private Demande demandePrioriteMoyenne;
	
	private ReservationDemandeDTOAssembleur assembleur;
	private ReservationDemandeDTO demandeDTO;

	@Before
	public void initialisation() {
		demandeDTO = new ReservationDemandeDTO();
		demandeDTO.nombrePersonnes = NB_PERSONNES;
		demandeDTO.courrielOrganisateur = COURRIEL_ORGANISATEUR;
		demandeDTO.priorite = PRIORITE_MOYENNE;
		demandeDTO.courrielParticipants = COURRIEL_PARTICIPANTS;
		assembleur = new ReservationDemandeDTOAssembleur();
		
		demandePrioriteMoyenne = new Demande(null, null, Priorite.moyenne());
	}
	
	@Test
	public void etantDonneUnDemandeDTOAvecUnNombreDePersonnesXQuandAssembleDemandeAlorsRetourneDemandeAvecNombreDeParticipantsX(){
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertEquals(NB_PERSONNES, demandeResultat.getNbParticipants());
	}
	
	@Test
	public void etantDonneUnDemandeDTOAvecCourrielOrganisateurXQuandAssembleDemandeAlorsRetourneDemandeAvecCourrielOrganisateurX(){
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertEquals(COURRIEL_ORGANISATEUR, demandeResultat.getOrganisateur().courriel);
	}
	
	@Test
	public void etantDonneUnDemandeDTOAvecCourrielOrganisateurXQuandAssembleDemandeAlorsRetourneDemandeAvecCourrielResponsableX(){
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertEquals(COURRIEL_ORGANISATEUR, demandeResultat.getResponsable().courriel);
	}
	
	@Test
	public void etantDonneUnDemandeDTOAvecPrioriteXQuandAssembleDemandeAlorsRetourneDemandeAvecPrioriteX(){
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		assertTrue(demandeResultat.estAussiPrioritaire(demandePrioriteMoyenne));
	}
	
	@Test
	public void etantDonneUnDemandeDTOLaListeDeCourrielDeParticipantsQuandAssembleDemandeAlorsRetourneDemandeAvecDesParticipantsAvecLesCourriels(){
		Demande demandeResultat = assembleur.assemblerDemande(demandeDTO);
		
		List<Object> listeCourrielParticipants = getListeCourrielParticipants(demandeResultat);
		assertTrue(listeCourrielParticipants.containsAll(COURRIEL_PARTICIPANTS));
	}

	private List<Object> getListeCourrielParticipants(Demande demandeResultat) {
		List<Employe> participants = demandeResultat.getParticipants();
		Object[] courrielParticipants = participants.stream().map(p -> p.courriel).toArray();
		List<Object> listeCourrielParticipants = java.util.Arrays.asList(courrielParticipants);
		return listeCourrielParticipants;
	}
}
