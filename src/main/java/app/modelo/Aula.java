package app.modelo;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "aulas")
public class Aula {
	
	@EmbeddedId
	private AulaPK pk = new AulaPK();
	
	@MapsId("professorId")
	@ManyToOne
	@JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = false)
	private Professor professor;
	
	@MapsId("disciplinaId")
	@ManyToOne
	@JoinColumn(name = "disciplina_id", referencedColumnName = "id", nullable = false)
	private Disciplina disciplina;
	
	@MapsId("turmaId")
	@ManyToOne
	@JoinColumn(name = "turma_id", referencedColumnName = "id", nullable = false)
	private Turma turma;

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		Funcionalidades.testarObjetoNulo.apply(professor);
		if (this.professor != null)
			this.professor.removerAula(this);
		professor.adicionarAula(this);
		this.professor = professor;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		Funcionalidades.testarObjetoNulo.apply(disciplina);
		if (this.disciplina != null)
			this.disciplina.removerAula(this);
		disciplina.adicionarAula(this);
		this.disciplina = disciplina;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		Funcionalidades.testarObjetoNulo.apply(turma);
		if (this.turma != null)
			this.turma.removerAula(this);
		turma.adicionarAula(this);
		this.turma = turma;
	}
	
	
	
}
