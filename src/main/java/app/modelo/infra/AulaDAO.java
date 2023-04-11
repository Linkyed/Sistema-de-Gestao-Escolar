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

			incluirAtomico(a);
			return a;
	}
	
	private Aula verificarExistencia(Professor profId, Disciplina discId, Turma turmaId) {
		return consutlarUm(OBTER_AULA_SQL, "discId", discId, "profId", profId, "turmaId", turmaId); 
	}
	
}
