package org.ClAssignateur.services.reservations;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import org.ClAssignateur.domaine.assignateur.AssignateurSalle;
import org.ClAssignateur.domaine.demandes.ConteneurDemandes;
import org.ClAssignateur.domaine.demandes.Demande;
import org.ClAssignateur.domaine.notification.Notificateur;
import org.ClAssignateur.services.reservations.ServiceReservationSalle;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTO;
import org.ClAssignateur.services.reservations.dto.ReservationDemandeDTOAssembleur;
import org.ClAssignateur.services.reservations.minuterie.Minute;
import org.ClAssignateur.services.reservations.minuterie.Minuterie;
import org.mockito.InOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

public class ServiceReservationSalleTest {

	private final Minute FREQUENCE_PAR_DEFAUT = new Minute(1);
	private final Minute FREQUENCE_MINUTES = new Minute(3);
	private final int LIMITE_DEMANDES_PAR_DEFAUT = 10;
	private final int LIMITE_DEMANDES_QUELCONQUE = 5;
	private final String TITRE_DEMANDE_A_ANNULER = "DemandeAnnulee";
	private final UUID ID_DEMANDE = UUID.randomUUID();

	private ReservationDemandeDTO dto;
	private Minuterie minuterie;
	private AssignateurSalle assignateur;
	private Demande demande;
	private Demande demandeAAnnuler;
	private ReservationDemandeDTOAssembleur assembleur;
	private ConteneurDemandes conteneurDemandes;
	private Notificateur notificateur;

	private ServiceReservationSalle serviceReservation;

	@Before
	public void creerServiceReservation() {
		minuterie = mock(Minuterie.class);
		assignateur = mock(AssignateurSalle.class);
		demande = mock(Demande.class);
		demandeAAnnuler = mock(Demande.class);
		assembleur = mock(ReservationDemandeDTOAssembleur.class);
		conteneurDemandes = mock(ConteneurDemandes.class);
		notificateur = mock(Notificateur.class);

		given(assembleur.assemblerDemande(dto)).willReturn(demande);

		serviceReservation = new ServiceReservationSalle(minuterie, conteneurDemandes, assignateur, notificateur,
				assembleur);
	}

	@Test
	public void configureMinuteriePendantDemarrageService() {
		verify(minuterie).setDelai(FREQUENCE_PAR_DEFAUT);
	}

	@Test
	public void serviceSouscritNotificationMinuteriePendantDemarrageService() {
		verify(minuterie).souscrire(serviceReservation);
	}

	@Test
	public void demarreMinuteriePendantDemarrageService() {
		verify(minuterie).demarrer();
	}

	@Test
	public void quandSetFrequenceDevraitChangerFrequenceMinuterieAvantRedemarrerMinuterie() {
		serviceReservation.setFrequence(FREQUENCE_MINUTES);
		InOrder inOrder = inOrder(minuterie);
		inOrder.verify(minuterie).setDelai(FREQUENCE_MINUTES);
		inOrder.verify(minuterie).reinitialiser();
	}

	@Test
	public void quandAjouterDemandeDevraitCreerDemandeAvecAssembleur() {
		serviceReservation.ajouterDemande(dto);
		verify(assembleur).assemblerDemande(dto);
	}

	@Test
	public void quandAjouterDemandeDevraitMettreDemandeEnAttente() {
		serviceReservation.ajouterDemande(dto);
		verify(conteneurDemandes).mettreDemandeEnAttente(demande);
	}

	@Test
	public void quandAjouterDemandeRenvoieIDDemande() {
		given(demande.getID()).willReturn(ID_DEMANDE);
		UUID idRecu = serviceReservation.ajouterDemande(dto);
		assertEquals(ID_DEMANDE, idRecu);
	}

	@Test
	public void etantDonneLimiteDemandeEnAttenteAtteinteQuandAjouterDemandeDevraitLancerAssignation() {
		given(conteneurDemandes.getNombreDemandesEnAttente()).willReturn(LIMITE_DEMANDES_PAR_DEFAUT);
		serviceReservation.ajouterDemande(dto);
		verify(assignateur).lancerAssignation(conteneurDemandes, notificateur);
	}

	@Test
	public void etantDonneDemandeAAnnulerExistanteQuandAnnulerDemandeDevraitAnnulerReservation() {
		permettreTrouverDemandeAAnnuler();
		serviceReservation.annulerDemande(TITRE_DEMANDE_A_ANNULER);
		verify(demandeAAnnuler).annulerReservation();
	}

	@Test
	public void etantDonneDemandeAAnnulerExistanteQuandAnnulerDemandeDevraitArchiver() {
		permettreTrouverDemandeAAnnuler();
		serviceReservation.annulerDemande(TITRE_DEMANDE_A_ANNULER);
		verify(conteneurDemandes).archiverDemande(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeAAnnulerExistanteQuandAnnulerDemandeDevraitNotifierAnnulation() {
		permettreTrouverDemandeAAnnuler();
		serviceReservation.annulerDemande(TITRE_DEMANDE_A_ANNULER);
		verify(notificateur).notifierAnnulation(demandeAAnnuler);
	}

	@Test
	public void etantDonneDemandeAAnnulerInexistanteQuandAnnulerDemandeDevraitNeRienFaire() {
		given(conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_DEMANDE_A_ANNULER)).willReturn(Optional.empty());
		serviceReservation.annulerDemande(TITRE_DEMANDE_A_ANNULER);
		verify(conteneurDemandes, never()).archiverDemande(any(Demande.class));
	}

	@Test
	public void etantDonneNouvelleLimiteDemandeEnAttenteAtteinteQuandSetLimiteDemandesAvantAssignationDevraitDemanderAssignationDemandes() {
		given(conteneurDemandes.getNombreDemandesEnAttente()).willReturn(LIMITE_DEMANDES_QUELCONQUE);
		serviceReservation.setLimiteDemandesAvantAssignation(LIMITE_DEMANDES_QUELCONQUE);
		verify(assignateur).lancerAssignation(conteneurDemandes, notificateur);
	}

	@Test
	public void quandNotifierDelaiEcouleDevraitLancerAssignation() {
		serviceReservation.notifierDelaiEcoule();
		verify(assignateur).lancerAssignation(conteneurDemandes, notificateur);
	}

	private void permettreTrouverDemandeAAnnuler() {
		given(conteneurDemandes.trouverDemandeSelonTitreReunion(TITRE_DEMANDE_A_ANNULER)).willReturn(
				Optional.of(demandeAAnnuler));
	}

}
