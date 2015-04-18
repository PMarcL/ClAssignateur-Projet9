package org.ClAssignateur.domaine.contacts;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class ContactsReunionTest {

	InformationsContact organisateur;
	InformationsContact responsable;
	InformationsContact participant;
	List<InformationsContact> groupeParticipants;

	ContactsReunion contacts;

	@Before
	public void initialisation() {
		organisateur = mock(InformationsContact.class);
		responsable = mock(InformationsContact.class);
		participant = mock(InformationsContact.class);

		groupeParticipants = new ArrayList<>();
		groupeParticipants.add(participant);

		contacts = new ContactsReunion(organisateur, responsable, groupeParticipants);
	}

	@Test
	public void etantDonneDeuxInstancesIdentiqueQuandEqualsDevraitRetournerVrai() {
		ContactsReunion contactsIdentique = new ContactsReunion(organisateur, responsable, groupeParticipants);
		assertTrue(contacts.equals(contactsIdentique));
	}

	@Test
	public void etantDonneOrganisateurDifferentQuandEqualsDevraitRetournerFaux() {
		InformationsContact autreOrganisateur = mock(InformationsContact.class);
		ContactsReunion contactOrganisateurDifferent = new ContactsReunion(autreOrganisateur, responsable,
				groupeParticipants);
		assertFalse(contacts.equals(contactOrganisateurDifferent));
	}

	@Test
	public void etantDonneResponsableDifferentQuandEqualsDevraitRetournerFaux() {
		InformationsContact autreResponsable = mock(InformationsContact.class);
		ContactsReunion contactResponsableDifferent = new ContactsReunion(organisateur, autreResponsable,
				groupeParticipants);
		assertFalse(contacts.equals(contactResponsableDifferent));
	}

	@Test
	public void etantDonneParticipantsDifferentQuandEqualsDevraitRetournerFaux() {
		InformationsContact autreParticipant = mock(InformationsContact.class);
		List<InformationsContact> autreGroupeParticipants = new ArrayList<>();
		autreGroupeParticipants.add(autreParticipant);
		ContactsReunion contactParticipantsDifferent = new ContactsReunion(organisateur, responsable,
				autreGroupeParticipants);

		assertFalse(contacts.equals(contactParticipantsDifferent));
	}

	@Test
	public void etantDonneAutreTypeQuandEqualsDevraitRetournerFaux() {
		final String OBJET_TYPE_DIFFERENT = "";
		assertFalse(contacts.equals(OBJET_TYPE_DIFFERENT));
	}
}
