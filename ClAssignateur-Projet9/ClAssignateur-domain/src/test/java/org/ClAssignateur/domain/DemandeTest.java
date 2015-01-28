package org.ClAssignateur.domain;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.w3c.dom.ranges.RangeException;
import org.junit.Before;
import java.util.Date;
import org.junit.Test;

public class DemandeTest {

	private static final Calendar DATE_DEBUT = creerDate(2015,07,1, 12,29,0);
	private static final Calendar DATE_FIN = creerDate(2015,07,1, 12,30,0);
	private static final Organisateur ORGANISATEUR = new Organisateur("Simon");
	private static final int NOMBRE_PARTICIPANT = 10;
	private static final int NOMBRE_PARTICIPANT_INCORRECTE = 0;
	
	private Demande demande;
	
	public static Calendar creerDate(int annee, int mois, int jour, int heure,int minute, int seconde) {
	    Calendar date = Calendar.getInstance();
	    date.set(annee, mois, jour, heure, minute, seconde);
	    return date;
	}
	
	@Before
	public void creerLaDemande(){
		demande = new Demande(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);
	}
	
	@Test
	public void DemandePossedeIntialementLeChampsDebutCommeDefiniDansLeConstructeur(){
		Calendar dateDebut = demande.getDebut();
		assertEquals(DATE_DEBUT, dateDebut);
	}
	
	@Test
	public void DemandePossedeIntialementLeChampsFinCommeDefiniDansLeConstructeur(){
		Calendar dateFin = demande.getFin();
		assertEquals(DATE_FIN, dateFin);
	}
	
	@Test
	public void DemandePossedeIntialementLeChampsNbParticipantCommeDefiniDansLeConstructeur(){
		int nbParticipant = demande.getNbParticipant();
		assertEquals(NOMBRE_PARTICIPANT, nbParticipant);
	}
	
	@Test
	public void DemandePossedeIntialementLeChampsOrganisateurCommeDefiniDansLeConstructeur(){
		Organisateur organisateur = demande.getOrganisateur();
		assertEquals(ORGANISATEUR, organisateur);
	}
	
	@Test(expected = RangeException.class)
	public void DemandeDoitAvoirUneDateDeDebutInferieurALaDateDeFin(){
		new Demande(DATE_FIN, DATE_DEBUT, NOMBRE_PARTICIPANT, ORGANISATEUR);
	}
	
	@Test(expected = RangeException.class)
	public void DemandeDoitAvoirUneDateDeDebutStrictementInferieurALaDateDeFin(){
		new Demande(DATE_FIN, DATE_FIN, NOMBRE_PARTICIPANT, ORGANISATEUR);
	}
	
	@Test(expected = RangeException.class)
	public void DemandeDoitAvoirUneNombreDeParticipantsStrictementSuperieurAuNombreDeParticipantMinimum(){
		new Demande(DATE_DEBUT, DATE_FIN, NOMBRE_PARTICIPANT_INCORRECTE, ORGANISATEUR);
	}
}
