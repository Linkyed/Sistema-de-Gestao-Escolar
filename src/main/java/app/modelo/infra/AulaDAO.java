package app.modelo.infra;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.Aula;
import app.modelo.Disciplina;
import app.modelo.Professor;
import app.modelo.Turma;

public class AulaDAO extends DAO<Aula>{
	
	static final String OBTER_AULA_SQL = "obterAulaUnica";
	
	public AulaDAO() {
		super(Aula.class);
	}
	
	public Aula obterAula(Professor profId, Disciplina discId, Turma turmaId) {
		Aula a = verificarExistencia(profId, discId, turmaId);
		if (a == null) throw new ConsultaNulaException("Nenhum aluno encontrado.");
		
		return a;
	}

	public Aula criarAula(Aula a) {
		if (verificarExistencia(a.getProfessor(), a.getDisciplina(), a.getTurma()) == null) {
			incluirAtomico(a);
			return a;
		} else {
			throw new RegistroDuplicadoException("A aula já existe no Banco de Dados.");			
		}
	}
	
	public Aula removerAula(Professor profId, Disciplina discId, Turma turmaId) {
		Aula a = verificarExistencia(profId, discId, turmaId);
		
		if (a != null) {
			a.getProfessor().removerAula(a);
			a.getDisciplina().removerAula(a);
			a.getTurma().removerAula(a);
			removerEntidade(a);
			return a;
		} else {
			throw new ConsultaNulaException("Nenhuma aula encontrada para ser excluido.");
		}				
	}
	
	private Aula verificarExistencia(Professor profId, Disciplina discId, Turma turmaId) {
		return consutlarUm(OBTER_AULA_SQL, "discId", discId, "profId", profId, "turmaId", turmaId); 
	}
	
}
