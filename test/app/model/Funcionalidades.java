package app.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Funcionalidades {
	
	private static final String REGEX_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final Pattern PADRAO_EMAIL = Pattern.compile(REGEX_EMAIL);
	
	static public String primeiraLetraMaiuscula(String texto) {
		String[] textoSeparado = texto.split(" ");
		String novoTexto = "";
		for (String partTexto: textoSeparado) {
			novoTexto = novoTexto + partTexto.substring(0, 1).toUpperCase() + partTexto.substring(1).toLowerCase() + " ";
		}
		return novoTexto.trim();
	}
	
	
	 public static boolean verificarEmail(String email) {
	        Matcher matcher = PADRAO_EMAIL.matcher(email);
	        return matcher.matches();
	    }
	
	 static public String verificarStringVazia(String texto) {
		 if (texto == null) throw new NullPointerException("Stringa nula.");
		 texto = texto.trim();
		 if (texto.isEmpty()) throw new IllegalArgumentException("String vazia.");
		 return texto;
	 }
}
