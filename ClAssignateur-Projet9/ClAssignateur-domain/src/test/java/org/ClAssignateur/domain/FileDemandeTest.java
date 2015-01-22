package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.FileDemande;

public class FileDemandeTest {

	private static final Date DATE_DEBUT = new Date(2015,07,1, 12,29,0);
	private static final Date DATE_FIN = new Date(2015,07,1, 12,30,0);
	private static final int NOMBRE_PARTICIPANT = 10;
	
	private FileDemande fileDemande;

	@Before
	public void creerFileDemande(){
		fileDemande = new FileDemande();
	}
	
	@Test
	public void fileDemandeEstInitialementVide(){
		boolean fileEstVide = fileDemande.Taille() == 0;
		
		assertTrue(fileEstVide);
	}
	
	@Test
	public void lorsqueOnAjouteUneDemandeAFileDemandeSaTailleAugmenteDeUn(){
		int tailleInitiale = fileDemande.Taille();
		fileDemande.Ajouter(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT);
		
		int tailleDeLaFile = fileDemande.Taille();
		int tailleDesiree = tailleInitiale+1;
		
		assertEquals(tailleDesiree,  tailleDeLaFile);
	}
	
	@Test
	public void lorsqueOnVideLaFileElleDevientVide(){
		fileDemande.Ajouter(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT);
		
		fileDemande.Vider();
		boolean fileEstVide = fileDemande.Taille() == 0;
		
		assertTrue(fileEstVide);
	}

}
