package app.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "turmas")
public class Turma {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String codigo;
	
	@Column(nullable = false, length = 25)
	private String tipoEnsino;
	
	@Column(nullable = false, length = 1)
	private String letraTurma;
	
	@Column(nullable = false, length = 80)
	private String sala;
	
	@OneToMany(mappedBy = "turma")
	private List<Aluno> alunos;
	
	@ManyToMany(mappedBy = "turmas", cascade = CascadeType.MERGE)
	private List<Professor> professores;

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
		if (NivelEscolar.ENSINO_MEDIO.equals(Funcionalidades.StringParaNivelEscolar(tipoEnsino))) {
			primeiraParte = "EM";
		} else if (NivelEscolar.FUNDAMENTAL.equals(Funcionalidades.StringParaNivelEscolar(tipoEnsino))) {
			primeiraParte = "EF";
		}
		
		this.codigo = String.format("%s%s", primeiraParte, letraTurma);
		
	}

	public String getTipoEnsino() {
		return tipoEnsino;
	}

	public void setTipoEnsino(NivelEscolar tipoEnsino) {
		Funcionalidades.testarObjetoNulo.apply(tipoEnsino);
		this.tipoEnsino = Funcionalidades.nivelEscolarParaString(tipoEnsino);
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

	public List<Aluno> getAlunos() {
		if (alunos == null) alunos = new ArrayList<>();
		return alunos;
	}

	public List<Professor> getProfessores() {
		if (professores == null) professores = new ArrayList<>();
		return professores;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
	
}
