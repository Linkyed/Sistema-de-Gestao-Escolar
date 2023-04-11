package app.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class AulaPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long professorId;
	
	private Long disciplinaId;
	
	private Long turmaId;
	
	public AulaPK() {
		// TODO Auto-generated constructor stub
	}

	public Long getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Long professorId) {
		this.professorId = professorId;
	}

	public Long getDisciplinaId() {
		return disciplinaId;
	}

	public void setDisciplinaId(Long disciplinaId) {
		this.disciplinaId = disciplinaId;
	}

	public Long getTurmaId() {
		return turmaId;
	}

	public void setTurmaId(Long turmaId) {
		this.turmaId = turmaId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(disciplinaId, professorId, turmaId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AulaPK other = (AulaPK) obj;
		return Objects.equals(disciplinaId, other.disciplinaId) && Objects.equals(professorId, other.professorId)
				&& Objects.equals(turmaId, other.turmaId);
	}

	
	
}
