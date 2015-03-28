package org.ClAssignateur.interfaces;

import java.util.UUID;
import org.ClAssignateur.domain.salles.Salle;
import org.ClAssignateur.domain.groupe.Employe;

public class DemandeDTO {

	public int nbParticipants;
	public Employe organisateur;
	public Employe responsable;
	public UUID identifiant;
	public Salle salleAssignee;

}
