package app.excecao;

public class ConsultaNulaException extends RuntimeException{

	private static final long serialVersionUID = -4623305146130644217L;

	String message;
	
	public ConsultaNulaException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
