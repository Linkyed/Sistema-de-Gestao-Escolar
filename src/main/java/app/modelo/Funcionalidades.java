package app.modelo;

import java.sql.Date;
import java.util.Calendar;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Funcionalidades {

	private static final String REGEX_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	private static final Pattern PADRAO_EMAIL = Pattern.compile(REGEX_EMAIL);

	private static final String REGEX_CPF = "([0-9]{11})";
	private static final Pattern PADRAO_CPF = Pattern.compile(REGEX_CPF);

	public static Function<Object, Object> testarObjetoNulo = o -> {
		if (o == null)
			throw new NullPointerException("Objeto nulo.");
		return o;
	};

	public static Function<String, String> testarStringNula = s -> {
		if (s == null)
			throw new NullPointerException("String nula.");
		return s;
	};
	public static Function<String, String> testarStringVazia = s -> {
		s = s.trim();
		if (s.isEmpty())
			throw new IllegalArgumentException("String vazia.");
		return s;
	};

	public static Function<String, String> testarSoLetras = s -> {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetter(s.charAt(i)) && !Character.isSpaceChar(s.charAt(i))) {
				throw new IllegalArgumentException("Só letras.");
			}
		}
		return s;
	};
	
	public static Function<String, String> testarSoNumeros = s -> {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i)) && !Character.isSpaceChar(s.charAt(i))) {
				throw new IllegalArgumentException("Só numeros.");
			}
		}
		return s;
	};

	static public String todaPrimeiraLetraMaiuscula(String texto) {
		texto = testarStringNula.andThen(testarStringVazia).apply(texto);
		String[] textoSeparado = texto.split(" ");
		String novoTexto = "";
		for (String partTexto : textoSeparado) {
			novoTexto = novoTexto + partTexto.substring(0, 1).toUpperCase() + partTexto.substring(1).toLowerCase()
					+ " ";
		}
		return novoTexto.trim();
	}
	
	static public String apenasPrimeiraLetraMaiscula(String texto) {
		texto = testarStringNula.andThen(testarStringVazia).apply(texto);
		texto = texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
		return texto;
				
	}

	static public String verificarStringVazia(String texto) {
		if (texto == null)
			throw new NullPointerException("Stringa nula.");
		texto = texto.trim();
		if (texto.isEmpty())
			throw new IllegalArgumentException("String vazia.");
		return texto;
	}

	static public String verificarSexo(String sexo) {
		sexo = Funcionalidades.apenasPrimeiraLetraMaiscula(sexo);
		if ("feminino".equalsIgnoreCase(sexo) || "masculino".equalsIgnoreCase(sexo) || "outro".equalsIgnoreCase(sexo)) {
			return sexo;			
		} else {
			throw new IllegalArgumentException("Sexo invalido");
		}
	}
	
	static public String verificarEmail(String email) {
		email = testarStringNula.andThen(testarStringVazia).apply(email);
		Matcher matcher = PADRAO_EMAIL.matcher(email);
		if (matcher.matches())
			return email;
		else
			throw new IllegalArgumentException("Email invalido.");
	}

	// Formato da data dd-mm-aaaa
	static public Date cirarDataSQL(String date) {
		Integer[] dataSeparada = converterStringPraData(date);
		verificarData(dataSeparada[0], dataSeparada[1], dataSeparada[2]);
     		Calendar calendario = Calendar.getInstance();
		calendario.set(dataSeparada[2], dataSeparada[1] - 1, dataSeparada[0]);
		java.util.Date data = calendario.getTime();
		Date sqlData = new java.sql.Date(data.getTime());

		return sqlData;
	}

	static public Integer[] converterStringPraData(String data) {
		Integer[] dataVetor = new Integer[3];
		try {
			String[] dataSeparada = data.split("-");
			int index = 0;
			for (String parteData : dataSeparada) {
				dataVetor[index] = Integer.parseInt(parteData);
				index++;
			}
			return dataVetor;
		} catch (PatternSyntaxException e) {
			throw new IllegalArgumentException("Formato da Data errado.");
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Valores de data errado.");
		}
	}

	public static String validarCPF(String cpf) {
		cpf = testarStringNula
				.andThen(testarStringVazia)
				.andThen(testarSoNumeros)
				.apply(cpf);
		
	    cpf = cpf.replaceAll("[^0-9]", "");

	    if (cpf.length() != 11) {
	        throw new IllegalArgumentException("CPF inválido: o número deve ter 11 dígitos.");
	    }
	    
	    boolean verificarTodosIguais = true;
	    
	    for (int i=1; i < cpf.length(); i++) {
	    	if (cpf.charAt(i) != cpf.charAt(i-1)) {
	    		verificarTodosIguais = false;
	    	}
	    }
	    
	    if (verificarTodosIguais == true) {
	    	throw new IllegalArgumentException("CPF invalido.");
	    }

	    int soma = 0;
	    for (int i = 0; i < 9; i++) {
	        soma += Integer.parseInt(cpf.substring(i, i+1)) * (10 - i);
	    }
	    int digito1 = 11 - (soma % 11);
	    if (digito1 > 9) {
	        digito1 = 0;
	    }

	    soma = 0;
	    for (int i = 0; i < 10; i++) {
	        soma += Integer.parseInt(cpf.substring(i, i+1)) * (11 - i);
	    }
	    int digito2 = 11 - (soma % 11);
	    if (digito2 > 9) {
	        digito2 = 0;
	    }

	    if (digito1 == Integer.parseInt(cpf.substring(9, 10)) && digito2 == Integer.parseInt(cpf.substring(10))) {
	        return cpf;
	    } else {
	        throw new IllegalArgumentException("CPF inválido.");
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

	static public Integer converterStringPraInteger(String numero) {
		numero = testarStringNula.andThen(testarStringVazia)
				.andThen(testarSoNumeros).apply(numero);
		try {
			Integer num = Integer.parseInt(numero);
			return num;
		} catch (NumberFormatException e) {
			throw e;
		}
	}

}
