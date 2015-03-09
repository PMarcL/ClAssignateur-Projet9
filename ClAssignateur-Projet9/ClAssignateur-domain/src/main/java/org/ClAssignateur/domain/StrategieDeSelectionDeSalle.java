package org.ClAssignateur.domain;

import java.util.Collection;
import java.util.Optional;

public interface StrategieDeSelectionDeSalle {

	public Optional<Salle> appliquer(Collection<Salle> salles, Demande demande);
}
