package org.ClAssignateur.domain;

import java.util.ArrayList;
import java.util.List;

public class StrategieDeclenchementAssignationComposite implements IStrategieDeclenchementAssignation {

	private List<IStrategieDeclenchementAssignation> strategies;

	public StrategieDeclenchementAssignationComposite() {
		this.strategies = new ArrayList<IStrategieDeclenchementAssignation>();
	}

	@Override
	public boolean verifierConditionAtteinte(IStrategieDeclenchementAssignationContexte contexte) {
		for (IStrategieDeclenchementAssignation strategie : strategies) {
			if (strategie.verifierConditionAtteinte(contexte))
				return true;
		}

		return false;
	}

	public void ajouterStrategie(IStrategieDeclenchementAssignation strategie) {
		this.strategies.add(strategie);
	}

}
