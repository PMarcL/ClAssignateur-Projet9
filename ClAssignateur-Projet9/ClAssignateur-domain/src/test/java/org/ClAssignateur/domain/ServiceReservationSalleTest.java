package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServiceReservationSalleTest {
	@Mock
	    private GestionnaireDemande monGestionnaireDemande;

	@Test
	public void QuandJeSetLaFrequenceAlorsElleEstModifiee(){
		ServiceReservationSalle monServiceReservation = new ServiceReservationSalle();
		int frequence;
		
		monServiceReservation.setFrequence(frequence);
		
		verify(monGestionnaireDemande, times(1)).setFrequence(frequence);
	}	
	
}
