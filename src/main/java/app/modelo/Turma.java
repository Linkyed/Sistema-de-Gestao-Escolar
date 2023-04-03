package app.modelo;

public class Turma {
	
	private Long id;
	
	private String codigo;
	
	private NivelEscolar tipoEnsino;
	
	private String letraTurma;
	
	private String sala;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public NivelEscolar getTipoEnsino() {
		return tipoEnsino;
	}

	public void setTipoEnsino(NivelEscolar tipoEnsino) {
		if (tipoEnsino == null) throw new NullPointerException("Tipo Ensino nulo.");
		this.tipoEnsino = tipoEnsino;
	}

	public String getLetraTurma() {
		return letraTurma;
	}

	public void setLetraTurma(String letraTurma) {
		letraTurma = Funcionalidades.verificarStringVazia(letraTurma);
		this.letraTurma = letraTurma;
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		this.sala = sala;
	}
	
	static public NivelEscolar StringParaNivelEscolar (String nvEsc) {
		if (nvEsc == null) throw new NullPointerException("String Nivel Escolar nula.");
		if ("fundamental".equalsIgnoreCase(nvEsc)) {
			return NivelEscolar.FUNDAMENTAL;
		} else if ("ensino medio".equalsIgnoreCase(nvEsc)) {
			return NivelEscolar.ENSINO_MEDIO;
		} else {
			throw new IllegalArgumentException("Disciplina invalida.");
		}
	}
	
}
