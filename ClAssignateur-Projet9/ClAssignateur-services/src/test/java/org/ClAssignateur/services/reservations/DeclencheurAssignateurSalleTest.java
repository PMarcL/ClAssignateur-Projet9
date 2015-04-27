package org.ClAssignateur.services.reservations;

import static org.mockito.BDDMockito.*;

import java.util.Optional;
import java.util.UUID;

import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

public class DeclencheurAssignateurSalleTest {
	private final Minute FREQUENCE_PAR_DEFAUT = new Minute(1);
	private final Minute FREQUENCE_3_MINUTES = new Minute(3);
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 100;
	private final int LIMITE_DEMANDES_5 = 5;
	private final int LIMITE_DEMANDES_7 = 7;
	private final String TITRE_DEMANDE_ANNULEE = "Demande annulée";
	private final UUID ID_DEMANDE = UUID.randomUUID();

	private Minuterie minuterie;
	private Demande demandeAjoutee;
	private Demande demandeAnnulee;
	private ConteneurDemandes conteneurDemandes;
	private AssignateurSalle assignateur;
	private Notificateur notificateur;

	private DeclencheurAssignateurSalle declencheur;

	@Before
	public void initialisation() {
		minuterie = mock(Minuterie.class);
		conteneurDemandes = mock(ConteneurDemandes.class);
		demandeAjoutee = mock(Demande.class);
		demandeAnnulee = mock(Demande.class);
		assignateur = mock(AssignateurSalle.class);
		notificateur = mock(Notificateur.class);

		declencheur = new DeclencheurAssignateurSalle(minuterie, conteneurDemandes, assignateur, notificateur);
	}

	@Test
	public void configureMinuteriePendantInitialisation() {
		verify(minuterie).setDelai(FREQUENCE_PAR_DEFAUT);
	}

	@Test
	public void souscritNotificationMinuteriePendantInitialisation() {
		verify(minuterie).souscrire(declencheur);
	}

	@Test
	public void demarreMinuteriePendantInitialisation() {
		verify(minuterie).demarrer();
	}

	@Test
	public void quandSetFrequenceDevraitChangerFrequenceMinuterieAvantRedemarrerMinuterie() {
		declencheur.setFrequence(FREQUENCE_3_MINUTES);
		InOrder inOrder = inOrder(minuterie);
		inOrder.verify(minuterie).setDelai(FREQUENCE_3_MINUTES);
		inOrder.verify(minuterie).reinitialiser();
	}

	@Test
	public void quandAjouterDemandeDevraitMettreDemandeEnAttente() {
		declencheur.ajouterDemande(demandeAjoutee);
		verify(conteneurDemandes).mettreDemandeEnAttente(demandeAjoutee);
	}

	@Test
	public void etantDonneLimiteDemandeEnAttenteAtteinteQuandAjouterDemandeDevraitLancerAssignation() {
		given(conteneurDemandes.getNombreDemandesEnAttente()).willReturn(LIMITE_DEMANDES_PAR_DEFAUT);
		declencheur.ajouterDemande(demandeAjoutee);
		verify(assignateur).lancerAssignation(conteneurDemandes, notificateur);
	}

	@Test
	public void etantDonneLimiteDemandeEnAttenteAtteinteQuandAjouterDeamndeDevraitReinitiliserMinuterie() {
		given(conteneurDemandes.getNombreDemandesEnAttente()).willReturn(LIMITE_DEMANDES_PAR_DEFAUT);
		declencheur.ajouterDemande(demandeAjoutee);
		verify(minuterie).reinitialiser();
	}

	@Test
	public void etantDonneDemandeAAnnulerExistanteQuandAnnulerDemandeDevraitAnnulerReservation() {
		permettreTrouverDemandeAAnnuler();
		declencheur.annulerDemande(TITRE_DEMANDE_ANNULEE);
		verify(demandeAnnulee).annulerReservation();
	}

	@Test
	public void etantDonneDemandeAAnnulerExistanteQuandAnnulerDemandeDevraitNotifierAnnulation() {
		permettreTrouverDemandeAAnnuler();
		declencheur.annulerDemande(TITRE_DEMANDE_ANNULEE);
		verify(notificateur).notifierAnnulation(demandeAnnulee);
	}

	@Test
	public void etantDonneDemandeAAnnulerInexistanteQuandAnnulerDemandeDevraitNeRienFaire() {
		given(conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_DEMANDE_ANNULEE)).willReturn(Optional.empty());
		declencheur.annulerDemande(TITRE_DEMANDE_ANNULEE);
		verify(conteneurDemandes, never()).archiverDemande(any(Demande.class));
	}

	@Test
	public void etantDonneDemandeAAnnulerAvecIDExistanteQuandAnnulerDemandeDevraitAnnulerReservation() {
		permettreTrouverDemandeAAnnulerAvecID();
		declencheur.annulerDemande(ID_DEMANDE);
		verify(demandeAnnulee).annulerReservation();
	}

	@Test
	public void etantDonneDemandeAAnnulerAvecIDExistanteQuandAnnulerDemandeDevraitNotifierAnnulation() {
		permettreTrouverDemandeAAnnulerAvecID();
		declencheur.annulerDemande(ID_DEMANDE);
		verify(notificateur).notifierAnnulation(demandeAnnulee);
	}

	@Test
	public void etantDonneDemandeAAnnulerAvecIDInexistanteQuandAnnulerDemandeDevraitNeRienFaire() {
		given(conteneurDemandes.obtenirDemandeSelonId(ID_DEMANDE)).willReturn(Optional.empty());
		declencheur.annulerDemande(ID_DEMANDE);
		verify(conteneurDemandes, never()).archiverDemande(any(Demande.class));
	}

	@Test
	public void etantDonneNouvelleLimiteDemandeEnAttenteAtteinteQuandSetLimiteDemandesAvantAssignationDevraitDemanderAssignationDemandes() {
		given(conteneurDemandes.getNombreDemandesEnAttente()).willReturn(LIMITE_DEMANDES_5);
		declencheur.setLimiteDemandesAvantAssignation(LIMITE_DEMANDES_5);
		verify(assignateur).lancerAssignation(conteneurDemandes, notificateur);
	}

	@Test
	public void etantDonneNouvelleLimiteDemandeSuperieureNbDemandesEnAttenteQuandSetLimiteDemandesAvantAssignationDevraitNeRienFaire() {
		given(conteneurDemandes.getNombreDemandesEnAttente()).willReturn(LIMITE_DEMANDES_5);
		declencheur.setLimiteDemandesAvantAssignation(LIMITE_DEMANDES_7);
		verify(assignateur, never()).lancerAssignation(any(ConteneurDemandes.class), any(Notificateur.class));
	}

	@Test
	public void quandNotifierDelaiEcouleDevraitLancerAssignation() {
		declencheur.notifierDelaiEcoule();
		verify(assignateur).lancerAssignation(conteneurDemandes, notificateur);
	}

	private void permettreTrouverDemandeAAnnuler() {
		given(conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_DEMANDE_ANNULEE)).willReturn(
				Optional.of(demandeAnnulee));
	}

	private void permettreTrouverDemandeAAnnulerAvecID() {
		given(conteneurDemandes.obtenirDemandeSelonId(ID_DEMANDE)).willReturn(Optional.of(demandeAnnulee));
	}
}
