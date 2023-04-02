package app.modelo;

import java.sql.Date;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Funcionalidades {
	
	private static final String REGEX_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final Pattern PADRAO_EMAIL = Pattern.compile(REGEX_EMAIL);
	
	private static final String REGEX_CPF = "([0-9]{11})";
	private static final Pattern PADRAO_CPF = Pattern.compile(REGEX_CPF);
	
	static public String primeiraLetraMaiuscula(String texto) {
		texto = verificarStringVazia(texto);
		String[] textoSeparado = texto.split(" ");
		String novoTexto = "";
		for (String partTexto: textoSeparado) {
			novoTexto = novoTexto + partTexto.substring(0, 1).toUpperCase() + partTexto.substring(1).toLowerCase() + " ";
		}
		return novoTexto.trim();
	}
		
	 static public String verificarStringVazia(String texto) {
		 if (texto == null) throw new NullPointerException("Stringa nula.");
		 texto = texto.trim();
		 if (texto.isEmpty()) throw new IllegalArgumentException("String vazia.");
		 return texto;
	 }
	 
	 static public boolean verificarEmail(String email) {
		 Matcher matcher = PADRAO_EMAIL.matcher(email);
		 return matcher.matches();
	 }
	 
	 //Formato da data dd-mm-aaaa
	 static public Date cirarDataSQL(String date) {
		Integer[] dataSeparada = converterStringPraData(date);
		verificarData(dataSeparada[0], dataSeparada[1], dataSeparada[2]);
		Calendar calendario = Calendar.getInstance();
		calendario.set(dataSeparada[2], dataSeparada[1]-1, dataSeparada[0]);
		java.util.Date data = calendario.getTime();
		Date sqlData = new java.sql.Date(data.getTime());
		
		return sqlData;
	 }
	 
	 static public Integer[] converterStringPraData(String data) {
		 Integer[] dataVetor = new Integer[3];
		 try {
			 String[] dataSeparada = data.split("-");
			 int index = 0;
			 for (String parteData: dataSeparada) {
				 dataVetor[index] = Integer.parseInt(parteData);
				 index++;
			 }
			 return dataVetor;
		 } catch (PatternSyntaxException e)  {
			throw new IllegalArgumentException("Formato da Data errado.");
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Valores de data errado.");
		}
	 }
	 
	 static public boolean verificarEscritaCPF(String CPF) {
		 Matcher matcher = PADRAO_CPF.matcher(CPF);
		 return matcher.matches();
	 }
	 
	 static public String verificarValidadeCPF(String CPF) {
		if (!verificarEscritaCPF(CPF)) {
			throw new IllegalArgumentException("Escrita CPF invalida.");
		}
		
		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(CPF.charAt(i)) * (10 - i);
		}
		int digito1 = 11 - (soma % 11);
		if (digito1 > 9) {
			digito1 = 0;
		}

		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(CPF.charAt(i)) * (11 - i);
		}
		int digito2 = 11 - (soma % 11);
		if (digito2 > 9) {
			digito2 = 0;
		}

		if ((Character.getNumericValue(CPF.charAt(9)) == digito1)
				&& (Character.getNumericValue(CPF.charAt(10)) == digito2)) {
			return CPF;
		} else {
			throw new IllegalArgumentException("CPF invalido.");			
		}
		
	 }
	 
	 static public void verificarData(int dia, int mes, int ano) {
		 if (ano < 0) {
			 throw new IllegalArgumentException("Ano negativo.");
		 }
		 if (mes < 1 && mes > 12) {
			 throw new IllegalArgumentException("Mes " + mes + " invalido.");
		 }
		 if ((mes == 2) && (dia < 1 || dia > 28)) {
			 throw new IllegalArgumentException("Dia invalido");
		 } else if (mes % 2 == 0 && (dia < 1 || dia > 30)) {
			 throw new IllegalArgumentException("Dia invalido");
		 } else if (mes % 2 != 0 && (dia < 1 || dia > 31)) {
			 throw new IllegalArgumentException("Dia invalido");
		 } 
	 }
	 
	 static public Double converterStringPraDouble(String numero) {
		try {
			Double num = Double.parseDouble(numero);
			return num;
		} catch (NumberFormatException e) {
			throw e;
		}
	 }
	 
}
