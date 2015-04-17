package org.ClAssignateur.domaine.contacts;

public class AdresseCourrielInvalideException extends RuntimeException {

	String adresseCourrielFautive;
	private static final long serialVersionUID = 1L;
	
	public AdresseCourrielInvalideException(String adresseCourrielFautive){
		super();
		this.adresseCourrielFautive = adresseCourrielFautive;
	}
	
	@Override
	public String getMessage(){
		return "Le courriel \"" + adresseCourrielFautive + "\" n'est pas valide";
	}

}
