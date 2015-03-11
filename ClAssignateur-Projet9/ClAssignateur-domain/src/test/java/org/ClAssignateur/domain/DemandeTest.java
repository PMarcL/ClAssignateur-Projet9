package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class DemandeTest {

	private final String TITRE_REUNION = "Mon titre";
	private final String TITRE_DIFFERENT = "Un autre titre";
	private Employe ORGANISATEUR = mock(Employe.class);
	private Employe RESPONSABLE = mock(Employe.class);
	private final Groupe GROUPE = new Groupe(ORGANISATEUR, RESPONSABLE, new ArrayList<Employe>());
	private final Groupe GROUPE_DIFFERENT = new Groupe(ORGANISATEUR, new Employe("courriel"), new ArrayList<Employe>());
	private final Priorite PRIORITE_PAR_DEFAUT = Priorite.basse();
	private final Priorite PRIORITE_MOYENNE = Priorite.moyenne();

	private Demande demande;

	@Before
	public void creerLaDemande() {
		demande = new Demande(GROUPE, TITRE_REUNION);
	}

	@Test
	public void demandePossedeInitialementLeChampsGroupeCommeDefiniDansLeConstructeur() {
		Groupe groupe = demande.getGroupe();
		assertEquals(GROUPE, groupe);
	}

	@Test
	public void demandePossedeInitialementLeChampTitre() {
		String titre = demande.getTitre();
		assertEquals(TITRE_REUNION, titre);
	}

	@Test
	public void demandePossedeParDefautPrioriteBasse() {
		Demande autreDemande = new Demande(GROUPE, TITRE_REUNION, PRIORITE_PAR_DEFAUT);
		assertTrue(demande.aLeMemeNiveauDePriorite(autreDemande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteCommeDefiniDansLeConstructeur() {
		Demande demandeAvecPriorite = new Demande(GROUPE, TITRE_REUNION, PRIORITE_MOYENNE);

		assertTrue(demandeAvecPriorite.aLeMemeNiveauDePriorite(demandeAvecPriorite));
		assertTrue(demandeAvecPriorite.estPlusPrioritaire(demande));
	}

	@Test
	public void demandePossedeIntialementLeChampsPrioriteALaValeurInitiale() {
		assertTrue(demande.aLeMemeNiveauDePriorite(demande));
	}

	@Test
	public void UneDemandeEstIdentiqueAElleMeme() {
		assertTrue(demande.equals(demande));
	}

	@Test
	public void UneDemandeEstDifferenteDUneDemandeAvecUnePrioriteDifferente() {
		Demande demandeDifferente = new Demande(GROUPE, TITRE_REUNION, PRIORITE_MOYENNE);
		assertFalse(demande.equals(demandeDifferente));
	}

	@Test
	public void UneDemandeEstDifferenteDUneDemandeAvecUnTitreDifferent() {
		Demande demandeDifferente = new Demande(GROUPE, TITRE_DIFFERENT, PRIORITE_PAR_DEFAUT);
		assertFalse(demande.equals(demandeDifferente));
	}

	@Test
	public void UneDemandeEstDifferentDuneDemandeAvecUnAutreGroupe() {
		Demande demandeDifferente = new Demande(GROUPE_DIFFERENT, TITRE_REUNION, PRIORITE_PAR_DEFAUT);
		assertFalse(demande.equals(demandeDifferente));
	}
}
