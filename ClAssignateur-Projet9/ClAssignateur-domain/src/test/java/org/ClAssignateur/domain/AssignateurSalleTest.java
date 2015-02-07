package org.ClAssignateur.domain;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

public class AssignateurSalleTest {

	private final int FREQUENCE = 10;
	private final int LIMITE = 15;
	private final int FILE_NOMBRE_DEMANDES = 10;
	private final boolean FILE_DEMANDES_VIDE = true;
	private final boolean FILE_DEMANDES_NON_VIDE = false;
	private final boolean ASSIGNATION_AUTORISE = true;

	private IStrategieDeclenchementAssignation strategie;
	private IStrategieDeclenchementAssignationContexte strategieContexte;
	private AssignateurSalle assignateur;
	private FileDemande fileDemande;
	private EntrepotSalles entrepotSalles;

	@Before
	public void intialement() {
		strategie = mock(IStrategieDeclenchementAssignation.class);
		assignateur = new AssignateurSalle(FREQUENCE, LIMITE, strategie);
		strategieContexte = (IStrategieDeclenchementAssignationContexte) assignateur;
		fileDemande = mock(FileDemande.class);
		entrepotSalles = mock(EntrepotSalles.class);
	}

	@Test
	public void etantDonnePlusieursDemandeQuandAssignationDevraitFournirNombreDemandes() {
		given(fileDemande.taille()).willReturn(FILE_NOMBRE_DEMANDES);

		assignateur.assignerDemandeSalle(fileDemande, entrepotSalles);

		int nbDemandes = assignateur.getNbDemandesAssignationCourantes();
		assertEquals(FILE_NOMBRE_DEMANDES, nbDemandes);
	}

	@Test
	public void etantDonneeAuMoinsUneSalleDansEntrepotQuandAssignationDevraitVerifierSiFileDemandesVide() {

	}

	@Test
	public void etantDonneAuMoinsUneDemandeQuandAssignationDevraitVerifierSiConditionAtteinte() {
		given(fileDemande.estVide()).willReturn(FILE_DEMANDES_NON_VIDE);
		assignateur.assignerDemandeSalle(fileDemande, entrepotSalles);
		verify(strategie).verifierConditionAtteinte(strategieContexte);
	}

	@Test
	public void etantDonneAucuneDemandeQuandAssignationDevraitNeRienFaire() {
		given(fileDemande.estVide()).willReturn(FILE_DEMANDES_VIDE);
		assignateur.assignerDemandeSalle(fileDemande, entrepotSalles);
		verify(strategie, never()).verifierConditionAtteinte(strategieContexte);
	}

	// @Test
	// public void
	// etantDonneAssignationAutoriseQuandAssignationDevraitRetirerDemandeFile()
	// throws Exception {
	// given(strategie.verifierConditionAtteinte(any(IStrategieDeclenchementAssignationContexte.class))).willReturn(
	// ASSIGNATION_AUTORISE);
	// assignateur.assignerDemandeSalle(fileDemande, entrepotSalles);
	// verify(fileDemande).retirer();
	// }

}
