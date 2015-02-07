package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;

public class DeclencheurAssignationSalleTest {

	private final int FREQUENCE_QUELCONQUE = 10;
	private final int LIMITE_QUELCONQUE = 15;
	private final boolean ASSIGNATION_AUTORISE = true;
	private final boolean ASSIGNATION_REFUSEE = false;
	private final int NOMBRE_DEMANDES = 7;
	private final int AUCUNE_DEMANDE = 0;
	private final boolean FILE_DEMANDE_VIDE = true;
	private final boolean ENTREPOT_VIDE = true;

	private IStrategieDeclenchementAssignation strategie;
	private DeclencheurAssignationSalle declencheurAssignation;
	private FileDemande fileDemande;
	private EntrepotSalles entrepotSalles;
	private AssignateurSalle assignateur;

	@Before
	public void intialement() {
		strategie = mock(IStrategieDeclenchementAssignation.class);
		assignateur = mock(AssignateurSalle.class);
		declencheurAssignation = new DeclencheurAssignationSalle(FREQUENCE_QUELCONQUE, LIMITE_QUELCONQUE, strategie,
				assignateur);
		fileDemande = mock(FileDemande.class);
		entrepotSalles = mock(EntrepotSalles.class);
	}

	@Test
	public void etantDonneUnNombreQuelconqueDemandesQuandVerificationDemandeeDevraitModifierNombreDemandesCourantes() {
		given(fileDemande.taille()).willReturn(NOMBRE_DEMANDES);
		declencheurAssignation.verifierConditionEtAssignerDemandeSalle(fileDemande, entrepotSalles);
		assertEquals(NOMBRE_DEMANDES, declencheurAssignation.getNbDemandesAssignationCourantes());
	}

	private IStrategieDeclenchementAssignationContexte strategieContexteQuelconque() {
		return any(IStrategieDeclenchementAssignationContexte.class);
	}

	private void autoriserAssignation() {
		given(strategie.verifierConditionAtteinte(strategieContexteQuelconque())).willReturn(ASSIGNATION_AUTORISE);
	}

	@Test
	public void etantDonneAssignationAutoriseeQuandVerificationDemandeeDevraitAssignerSalles() {
		autoriserAssignation();
		declencheurAssignation.verifierConditionEtAssignerDemandeSalle(fileDemande, entrepotSalles);
		verify(assignateur).assignerDemandeSalle(fileDemande, entrepotSalles);
	}

	private void verifierAssignationPasDeclenche() {
		verify(assignateur, never()).assignerDemandeSalle(any(FileDemande.class), any(EntrepotSalles.class));
	}

	private void refuserAssignation() {
		given(strategie.verifierConditionAtteinte(strategieContexteQuelconque())).willReturn(ASSIGNATION_REFUSEE);
	}

	@Test
	public void etantDonneAssignationRefuseeQuandVerificationDemandeeDevraitNePasAssignerSalles() {
		refuserAssignation();
		declencheurAssignation.verifierConditionEtAssignerDemandeSalle(fileDemande, entrepotSalles);
		verifierAssignationPasDeclenche();
	}

	@Test
	public void etantDonneAucuneDemandeEtConditionRempliesQuandVerificationDemandeeDevraitNePasAssignerSalles() {
		given(fileDemande.estVide()).willReturn(FILE_DEMANDE_VIDE);
		given(fileDemande.taille()).willReturn(AUCUNE_DEMANDE);
		autoriserAssignation();

		declencheurAssignation.verifierConditionEtAssignerDemandeSalle(fileDemande, entrepotSalles);

		verifierAssignationPasDeclenche();
	}

	@Test
	public void etantDonneAucuneSalleEtConditionRempliesQuandVerificationDemandeeDevraitNePasAssignerSalles() {
		given(entrepotSalles.estVide()).willReturn(ENTREPOT_VIDE);
		autoriserAssignation();

		declencheurAssignation.verifierConditionEtAssignerDemandeSalle(fileDemande, entrepotSalles);

		verifierAssignationPasDeclenche();
	}

	private boolean dateDerniereAssignationModifiee(Calendar avantVerification, Calendar apresVerification) {
		return apresVerification.compareTo(avantVerification) > 0;
	}

	public boolean verifierModificationDerniereAssignation() throws InterruptedException {
		Calendar avantVerification = declencheurAssignation.getDerniereAssignation();
		Thread.sleep(1);
		/*
		 * le sleep sert simplement à insérer un minime décalage entre la
		 * création de l'instance de DeclencheurAssignationSalle et l'appel de
		 * verifierCondition... pour vérifier si la date de dernière assignation
		 * est modifiée, car les tests s'exécutent trop rapidement.
		 */
		declencheurAssignation.verifierConditionEtAssignerDemandeSalle(fileDemande, entrepotSalles);
		Calendar apresVerification = declencheurAssignation.getDerniereAssignation();

		return dateDerniereAssignationModifiee(avantVerification, apresVerification);
	}

	@Test
	public void etanDonneConditionsRempliesQuandVerificationDemandeeDevraitModifierDerniereAssignation()
			throws InterruptedException {
		autoriserAssignation();

		boolean resultat = verifierModificationDerniereAssignation();

		assertTrue(resultat);
	}

	@Test
	public void etantDonneConditionNonRempliesQuandVerificationDemandeeDevraitNePasModifierDerniereAssignation()
			throws InterruptedException {
		refuserAssignation();

		boolean resultat = verifierModificationDerniereAssignation();

		assertFalse(resultat);
	}
}
