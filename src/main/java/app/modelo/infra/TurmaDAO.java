package app.modelo.infra;

import java.util.Iterator;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.AtributosAluno;
import app.modelo.AtributosProfessor;
import app.modelo.AtributosTurma;
import app.modelo.Disciplina;
import app.modelo.Funcionalidades;
import app.modelo.Professor;
import app.modelo.Turma;

public class TurmaDAO extends DAO<Turma>{
	
	private final static String OBTER_TURMA_SQL = "obterTurmaPorCodigo";
	
	
	public TurmaDAO() {
		super(Turma.class);
	}
	
	public Turma obterTurma(String codigo) {
		Turma t = verificarExistencia(codigo);
		if (t == null) throw new ConsultaNulaException("Nenhuma turma encontrada.");
		
		return t;
	}
	
	public Turma criarTurma(Turma t) {
		if (verificarExistencia(t.getCodigo()) == null) {
			incluirAtomico(t);
			return t;
		} else {
			throw new RegistroDuplicadoException("O codigo da turma já existe no Banco de Dados.");			
		}
	}
	
	public Turma removerTurma(String codigo) {
		Turma t = verificarExistencia(codigo);
		
		if (t != null) {
			t.getAlunos().forEach(a -> a.setTurma(null));
			t.getAulas().forEach(a -> {
					a.getDisciplina().removerAula(a);
					a.getProfessor().removerAula(a);
				});
			removerEntidade(t);
			return t;
		} else {
			throw new ConsultaNulaException("Nenhuma turma encontrado para ser excluida.");
		}			
	}
	
	public Turma Atualizar(String codigo, AtributosTurma escolhaAlteracao, String alteracao){
		Turma t = verificarExistencia(codigo);
		Funcionalidades.testarObjetoNulo.apply(t);
		Funcionalidades.testarObjetoNulo.apply(escolhaAlteracao);
		
		if (escolhaAlteracao.equals(AtributosTurma.NIVEL_TURMA)) {
			Turma teste = new Turma(t);
			teste.setNivelEscolar(alteracao);
			teste = verificarExistencia(teste.getCodigo());
			
			if (teste == null)
				t.setNivelEscolar(alteracao);
			else
				throw new RegistroDuplicadoException("Turma com o codigo já existe no Banco de Dados.");		
		}
		else if (escolhaAlteracao.equals(AtributosTurma.LETRA_TURMA)) {
			Turma teste = new Turma(t);
			teste.setLetraTurma(alteracao);
			teste = verificarExistencia(teste.getCodigo());
			
			if (teste == null)
				t.setLetraTurma(alteracao);
			else
				throw new RegistroDuplicadoException("Turma com o codigo já existe no Banco de Dados.");		
		}
		else if (escolhaAlteracao.equals(AtributosTurma.SALA)) 
			t.setSala(alteracao);
		
		mergeAtomico(t);
		return t;
	}
	
	private Turma verificarExistencia(String codigo) {
		return consutlarUm(OBTER_TURMA_SQL, "codigo", codigo); 
	}
	
}
