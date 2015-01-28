package org.ClAssignateur.domain;

public class StrategieAssignationAvecTemps implements IStrategieAssignation {

	private GestionnaireDemande contexte;

	public void assigner(GestionnaireDemande gestionnaireRecu) {
		Thread t = new Thread(new BoucleDAssignation());
		t.start();
	}

	private void affectuerAssignation() {

	}

	private class BoucleDAssignation implements Runnable {

		private long frequenceEnMilliSec;
		private long tempsDemarrage;

		public void run() {
			tempsDemarrage = System.currentTimeMillis();
			setFrequenceAssignation();
			while (true) {

				if (!(frequenceEnMilliSec == contexte.getFrequence() * 60 * 1000)) {
					setFrequenceAssignation();
					tempsDemarrage = System.currentTimeMillis();
				}

				if (System.currentTimeMillis() - tempsDemarrage >= frequenceEnMilliSec) {
					affectuerAssignation();
					tempsDemarrage = System.currentTimeMillis();
				}
			}
		}

		private void setFrequenceAssignation() {
			frequenceEnMilliSec = contexte.getFrequence() * 60 * 1000;
		}
	}
}
