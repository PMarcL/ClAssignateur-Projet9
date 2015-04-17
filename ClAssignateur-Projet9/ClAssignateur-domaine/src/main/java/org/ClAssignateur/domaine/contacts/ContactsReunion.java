package org.ClAssignateur.domaine.contacts;

import java.util.List;

public class ContactsReunion {

	public final InformationsContact organisateur;
	public final InformationsContact responsable;
	public final List<InformationsContact> participants;

	public ContactsReunion(InformationsContact organisateur, InformationsContact responsable,
			List<InformationsContact> participants) {
		this.organisateur = organisateur;
		this.responsable = responsable;
		this.participants = participants;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ContactsReunion))
			return false;

		ContactsReunion autreContacts = (ContactsReunion) obj;
		return this.organisateur.equals(autreContacts.organisateur)
				&& this.responsable.equals(autreContacts.responsable)
				&& comparerParticipants(autreContacts.participants);
	}

	private boolean comparerParticipants(List<InformationsContact> participantsAutreContacts) {
		return this.participants.containsAll(participantsAutreContacts);
	}
}
