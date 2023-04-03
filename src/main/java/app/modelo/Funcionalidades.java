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

	Predicate<String> testezada = n -> n == null;

	static Function<Object, Object> testarObjetoNulo = o -> {
		if (o == null)
			throw new NullPointerException("Objeto nulo.");
		return o;
	};

	static Function<String, String> testarStringNula = s -> {
		if (s == null)
			throw new NullPointerException("String nula.");
		return s;
	};
	static Function<String, String> testarStringVazia = s -> {
		s = s.trim();
		if (s.isEmpty())
			throw new IllegalArgumentException("String vazia.");
		return s;
	};

	static Function<String, String> testarSoLetras = s -> {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isLetter(s.charAt(i)) && !Character.isSpaceChar(s.charAt(i))) {
				throw new IllegalArgumentException("Só letras.");
			}
		}
		return s;
	};

	static public String primeiraLetraMaiuscula(String texto) {
		texto = testarStringNula.andThen(testarStringVazia).apply(texto);
		String[] textoSeparado = texto.split(" ");
		String novoTexto = "";
		for (String partTexto : textoSeparado) {
			novoTexto = novoTexto + partTexto.substring(0, 1).toUpperCase() + partTexto.substring(1).toLowerCase()
					+ " ";
		}
		return novoTexto.trim();
	}

	static public String verificarStringVazia(String texto) {
		if (texto == null)
			throw new NullPointerException("Stringa nula.");
		texto = texto.trim();
		if (texto.isEmpty())
			throw new IllegalArgumentException("String vazia.");
		return texto;
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

	static public Integer converterStringPraInteger(String numero) {
		try {
			Integer num = Integer.parseInt(numero);
			return num;
		} catch (NumberFormatException e) {
			throw e;
		}
	}

	static public boolean stringSoComLetras(String texto) {
		texto = verificarStringVazia(texto);

		boolean verificidaror = true;
		for (int i = 0; i < texto.length(); i++) {
			if (!Character.isLetter(texto.charAt(i)) && !Character.isSpaceChar(texto.charAt(i))) {
				verificidaror = false;
				return verificidaror;
			}
		}
		return verificidaror;
	}

	static public AreasDeConhecimento stringParaAreaConhecimento(String areConhe) {
		areConhe = Funcionalidades.testarStringNula.andThen(Funcionalidades.testarStringVazia).apply(areConhe);

		if ("geografia".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.GEOGRAFIA;
		else if ("artes".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.ARTES;
		else if ("biologia".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.BIOLOGIA;
		else if ("educação física".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.EDUCACAO_FISICA;
		else if ("filosofia".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.FILOSOFIA;
		else if ("história".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.HISTORIA;
		else if ("matemática".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.MATEMATICA;
		else if ("física".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.FISICA;
		else if ("química".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.QUIMICA;
		else if ("sociologia".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.SOCIOLOGIA;
		else if ("língua portuguesa".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.LINGUA_PORTUGUESA;
		else if ("literatura".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.LITERATURA;
		else if ("língua inglesa".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.LINGUA_INGLESA;
		else if ("língua alemã".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.LINGUA_ALEMA;
		else if ("língua francesa".equalsIgnoreCase(areConhe))
			return AreasDeConhecimento.LINGUA_FRANCESA;
		else {
			return null;
		}
	}

	static public String AreaConhecimentoParaString(AreasDeConhecimento disiplina) {
		Funcionalidades.testarObjetoNulo.apply(disiplina);

		if (disiplina.equals(AreasDeConhecimento.GEOGRAFIA))
			return "Geografia";
		else if (disiplina.equals(AreasDeConhecimento.ARTES))
			return "Artes";
		else if (disiplina.equals(AreasDeConhecimento.BIOLOGIA))
			return "Biologia";
		else if (disiplina.equals(AreasDeConhecimento.EDUCACAO_FISICA))
			return "Educação Física";
		else if (disiplina.equals(AreasDeConhecimento.FILOSOFIA))
			return "Filosofia";
		else if (disiplina.equals(AreasDeConhecimento.HISTORIA))
			return "História";
		else if (disiplina.equals(AreasDeConhecimento.MATEMATICA))
			return "Matemática";
		else if (disiplina.equals(AreasDeConhecimento.FISICA))
			return "Física";
		else if (disiplina.equals(AreasDeConhecimento.QUIMICA))
			return "Química";
		else if (disiplina.equals(AreasDeConhecimento.SOCIOLOGIA))
			return "Sociologia";
		else if (disiplina.equals(AreasDeConhecimento.LINGUA_PORTUGUESA))
			return "Língua Portuguesa";
		else if (disiplina.equals(AreasDeConhecimento.LITERATURA))
			return "Literatura";
		else if (disiplina.equals(AreasDeConhecimento.LINGUA_INGLESA))
			return "Língua Inglesa";
		else if (disiplina.equals(AreasDeConhecimento.LINGUA_ALEMA))
			return "Língua Alemã";
		else if (disiplina.equals(AreasDeConhecimento.LINGUA_FRANCESA))
			return "Língua Francesa";
		else
			return null;

	}

	static public NivelEscolar StringParaNivelEscolar(String nvEsc) {
		Funcionalidades.testarObjetoNulo.apply(nvEsc);

		if ("fundamental".equalsIgnoreCase(nvEsc)) {
			return NivelEscolar.FUNDAMENTAL;
		} else if ("ensino medio".equalsIgnoreCase(nvEsc)) {
			return NivelEscolar.ENSINO_MEDIO;
		} else {
			throw new IllegalArgumentException("Disciplina invalida.");
		}
	}

}
