package org.ClAssignateur.domaine.groupe;

import java.util.List;

public class ContactsReunion {

	private final InformationsContact organisateur;
	private final InformationsContact responsable;
	private final List<InformationsContact> participants;

	public ContactsReunion(InformationsContact organisateur, InformationsContact responsable, List<InformationsContact> participants) {
		this.organisateur = organisateur;
		this.responsable = responsable;
		this.participants = participants;
	}

	public InformationsContact getOrganisateur() {
		return this.organisateur;
	}

	public InformationsContact getResponsable() {
		return this.responsable;
	}

	public int getNbParticipants() {
		return participants.size();
	}

	public List<InformationsContact> getParticipants() {
		return participants;
	}

}
