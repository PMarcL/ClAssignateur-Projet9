package org.ClAssignateur.domain;

import org.junit.*;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class ServiceReservationSalleTest {
	
	int frequence = 3;
	int limite = 5;
	Date dateDebut;
    Date dateFin;
    int nbParticipants;
    String nomOrganisation;
     
	@Mock
	    private GestionnaireDemande monGestionnaireDemande;

	@Test
	public void QuandJeSetLaFrequenceAlorsElleDoitEtreModifieeParLeGestionnaire(){
		ServiceReservationSalle monServiceReservation = new ServiceReservationSalle();
			
		monServiceReservation.setFrequence(monGestionnaireDemande, frequence);
		
		verify(monGestionnaireDemande).setFrequence(frequence);
	}	
	
	@Test
	public void QuandJeSetLaLimiteAlorsElleDoitEtreModifieeParLeGestionnaire(){
		ServiceReservationSalle monServiceReservation = new ServiceReservationSalle();
		
		monServiceReservation.setLimite(monGestionnaireDemande, limite);
		
		verify(monGestionnaireDemande).setLimite(limite);	
	}
	
	@Test
	public void QuandJAjouteDemandeAlorsElleDoitEtreAjouteeAuGestionnaire(){
        ServiceReservationSalle monServiceReservation = new ServiceReservationSalle();
		
		monServiceReservation.ajouterDemande(monGestionnaireDemande, dateDebut, dateFin, nbParticipants, nomOrganisation);
		
		verify(monGestionnaireDemande).ajouterDemande(dateDebut, dateFin, nbParticipants, nomOrganisation);	
	}	
}
