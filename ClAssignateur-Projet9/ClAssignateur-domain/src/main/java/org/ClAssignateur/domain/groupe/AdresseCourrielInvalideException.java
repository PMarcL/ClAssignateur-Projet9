package org.ClAssignateur.domain.groupe;

public class AdresseCourrielInvalideException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	String cause;
	
	public AdresseCourrielInvalideException(String cause) {
		super(cause);
		this.cause = cause;
	}
	
	@Override
	public String getMessage(){
		return "Le courriel \""+ cause + "\" n'est pas valide";
	}

}
