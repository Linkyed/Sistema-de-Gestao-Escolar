package app.modelo;

import java.util.Arrays;
import java.util.List;

public class Turma {
	
	private Long id;
	
	private String codigo;
	
	private NivelEscolar tipoEnsino;
	
	private String letraTurma;
	
	private String sala;

	public Turma() {
		
	}
	
	public Turma(NivelEscolar tipoEnsino, String letraTurma, String sala) {
		setTipoEnsino(tipoEnsino);
		setLetraTurma(letraTurma);
		setSala(sala);
		setCodigo();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	private void setCodigo() {
		Funcionalidades.testarObjetoNulo.apply(tipoEnsino);
		Funcionalidades.testarObjetoNulo.apply(letraTurma);
		
		String primeiraParte = "";
		if (tipoEnsino.equals(NivelEscolar.ENSINO_MEDIO)) {
			primeiraParte = "EM";
		} else if (tipoEnsino.equals(NivelEscolar.FUNDAMENTAL)) {
			primeiraParte = "EF";
		}
		
		this.codigo = String.format("%s%s", primeiraParte, letraTurma);
		
	}

	public NivelEscolar getTipoEnsino() {
		return tipoEnsino;
	}

	public void setTipoEnsino(NivelEscolar tipoEnsino) {
		Funcionalidades.testarObjetoNulo.apply(tipoEnsino);
		this.tipoEnsino = tipoEnsino;
		if (letraTurma != null) {
			setCodigo();			
		}
	}

	public String getLetraTurma() {
		return letraTurma;
	}

	public void setLetraTurma(String letraTurma) {
		letraTurma = Funcionalidades.testarStringNula
				.andThen(Funcionalidades.testarStringVazia)
				.andThen(Funcionalidades.testarSoLetras).apply(letraTurma);
		if (letraTurma.length() != 1) throw new IllegalArgumentException("Letra da turma só pode conter 1 caracter.");
		this.letraTurma = letraTurma.toUpperCase();
		if (tipoEnsino != null) {
			setCodigo();			
		}
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		sala = Funcionalidades.testarStringNula
			.andThen(Funcionalidades.testarStringVazia).apply(sala);
		this.sala = sala;
	}
	
}
