package org.ClAssignateur.domain;


public class ProcessusAssignation {
	
	private GestionnaireDemande gestionnaireContexte;
	private boolean peutContinuer;
	
	public ProcessusAssignation(GestionnaireDemande contexteDonne) {
		gestionnaireContexte = contexteDonne;
		peutContinuer = false;
	}

	public void demarrer() {
		peutContinuer = true;
		(new Thread(new ProcessusConcurent())).start();
	}
	
	public void arreter() {
		peutContinuer = false;
	}
	
	private class ProcessusConcurent implements Runnable {
		public void run() {
			while(peutContinuer) {
				gestionnaireContexte.verifierConditionAssignation();
			}
		}
	}

}
