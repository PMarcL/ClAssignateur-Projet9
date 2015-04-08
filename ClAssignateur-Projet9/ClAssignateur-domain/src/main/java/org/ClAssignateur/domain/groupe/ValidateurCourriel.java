package org.ClAssignateur.domain.groupe;

import java.util.List;

public interface ValidateurCourriel {
	boolean validerCourriel(String courriel);
	boolean validerCourriel(List<String> courriel);
}
