package org.ClAssignateur.domaine.contacts;

import javax.persistence.OneToMany;

import javax.persistence.CascadeType;
import javax.persistence.OneToOne;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class ContactsReunion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@OneToOne(cascade = CascadeType.ALL)
	public final InformationsContact organisateur;
	@OneToOne(cascade = CascadeType.ALL)
	public final InformationsContact responsable;
	@OneToMany(cascade = CascadeType.ALL)
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
