package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.ClAssignateur.domain.FileDemande;

public class FileDemandeTest {

	private static final int TAILLE_INITIALE_VOULUE = 0;
	
	private static final Calendar DATE_DEBUT = creerDate(2015,07,1, 12,29,0);
	private static final Calendar DATE_FIN = creerDate(2015,07,1, 12,30,0);
	private static final Organisateur ORGANISATEUR = new Organisateur("Simon");
	private static final int NOMBRE_PARTICIPANT = 10;
	
	private static final Calendar DATE_DEBUT_2 = creerDate(2015,07,3, 12,29,0);
	private static final Calendar DATE_FIN_2 = creerDate(2015,07,4, 12,30,0);
	private static final int NOMBRE_PARTICIPANT_2 = 11;
	
	private static final Calendar DATE_DEBUT_3 = creerDate(2015,07,2, 12,29,0);
	private static final Calendar DATE_FIN_3 = creerDate(2015,07,3, 12,30,0);
	private static final int NOMBRE_PARTICIPANT_3 = 12;
	
	private FileDemande fileDemande;

	public static Calendar creerDate(int annee, int mois, int jour, int heure,int minute, int seconde) {
	    Calendar date = Calendar.getInstance();
	    date.set(annee, mois, jour, heure, minute, seconde);
	    return date;
	}
	
	@Before
	public void creerFileDemande(){
		fileDemande = new FileDemande();
	}
	
	@Test
	public void fileDemandeEstInitialementVide(){
		boolean fileEstVide = fileDemande.estVide();
		assertTrue(fileEstVide);
	}
	
	@Test
	public void filePossedeInitalementTailleZero(){
		int tailleInitiale = fileDemande.taille();
		assertEquals(TAILLE_INITIALE_VOULUE,  tailleInitiale);
	}
	
	@Test 
	public void lorsqueOnAjouteUneDemandeAFileDemandeLaFileNestPlusVide(){
		fileDemande.ajouter(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);
		
		boolean fileEstVide = fileDemande.estVide();
		
		assertFalse(fileEstVide);
	}
	
	@Test
	public void lorsqueOnAjouteUneDemandeAFileDemandeSaTailleAugmenteDeUn(){
		int tailleInitiale = fileDemande.taille();
		fileDemande.ajouter(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);
		
		int tailleDeLaFile = fileDemande.taille();
		int tailleDesiree = tailleInitiale+1;
		
		assertEquals(tailleDesiree,  tailleDeLaFile);
	}
	
	@Test
	public void lorsqueOnVideLaFileElleDevientVide(){
		fileDemande.ajouter(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);
		
		fileDemande.vider();
		boolean fileEstVide = fileDemande.estVide();
		
		assertTrue(fileEstVide);
	}
	
	@Test
	public void lorsqueOnAjouteUnElementEtQuOnLEnleveFileDemandeDevientVide(){
		fileDemande.ajouter(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);
		
		fileDemande.retirer();
		boolean fileEstVide = fileDemande.estVide();
		
		assertTrue(fileEstVide);
	}
	
	@Test
	public void lorsqueOnEnleveDesElementsLePremierArriveEstLePremierSorti(){
		FileDemande fileDemandeTroisElement = new FileDemande();
		fileDemandeTroisElement.ajouter(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);
		fileDemandeTroisElement.ajouter(DATE_DEBUT_2, DATE_FIN_2, NOMBRE_PARTICIPANT_2, ORGANISATEUR);
		fileDemandeTroisElement.ajouter(DATE_DEBUT_3, DATE_FIN_3, NOMBRE_PARTICIPANT_3, ORGANISATEUR);
		
		Demande premierElementEnlever = fileDemandeTroisElement.retirer();
		Demande deuxiemeElementEnlever = fileDemandeTroisElement.retirer();
		Demande troisiemeElementEnlever = fileDemandeTroisElement.retirer();
		
		assertEquals(DATE_DEBUT, premierElementEnlever.getDebut());
		assertEquals(DATE_FIN, premierElementEnlever.getFin());
		assertEquals(NOMBRE_PARTICIPANT, premierElementEnlever.getNbParticipant());
		assertEquals(DATE_DEBUT_2, deuxiemeElementEnlever.getDebut());
		assertEquals(DATE_FIN_2, deuxiemeElementEnlever.getFin());
		assertEquals(NOMBRE_PARTICIPANT_2, deuxiemeElementEnlever.getNbParticipant());
		assertEquals(DATE_DEBUT_3, troisiemeElementEnlever.getDebut());
		assertEquals(DATE_FIN_3, troisiemeElementEnlever.getFin());
		assertEquals(NOMBRE_PARTICIPANT_3, troisiemeElementEnlever.getNbParticipant());
	}

}
