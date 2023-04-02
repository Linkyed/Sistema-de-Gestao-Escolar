package app.excecao;

public class RegistroDuplicadoException extends RuntimeException{

	private static final long serialVersionUID = 8154218097838806585L;

	String message;
	
	public RegistroDuplicadoException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
