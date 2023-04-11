package app.modelo.infra;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.AtributosAluno;
import app.modelo.AtributosDisciplina;
import app.modelo.AtributosProfessor;
import app.modelo.Disciplina;
import app.modelo.Funcionalidades;
import app.modelo.Professor;

public class DisciplinaDAO extends DAO<Disciplina> {
	
	private final static String OBTER_DISCIPLINA_SQL = "obterDisciplinaPorCodigo";
	
	public DisciplinaDAO() {
		super(Disciplina.class);
	}
	
	public Disciplina obterDisciplina(String codigo) {
		Disciplina d = verificarExistencia(codigo);
		if (d == null) throw new ConsultaNulaException("Nenhuma disciplina encontrada.");
		
		return d;
	}
	
	public Disciplina criarDisciplina(Disciplina d) {
		if (verificarExistencia(d.getCodigo()) == null) {
			incluirAtomico(d);
			return d;
		} else {
			throw new RegistroDuplicadoException("O codigo da disciplina já existe no Banco de Dados.");			
		}
	}
	
	public Disciplina removerDisciplina(String codigo) {
		Disciplina d = verificarExistencia(codigo);
		
		if (d != null) {
			d.getAulas().forEach(a -> {
				a.getProfessor().removerAula(a);
				a.getTurma().removerAula(a);
			});
			removerEntidade(d);
			return d;
		} else {
			throw new ConsultaNulaException("Nenhuma disciplina encontrada para ser excluida.");
		}			
	}
	
	public Disciplina Atualizar(String codigo, AtributosDisciplina escolhaAlteracao, String alteracao){
		Disciplina d = verificarExistencia(codigo);
		Funcionalidades.testarObjetoNulo.apply(d);
		Funcionalidades.testarObjetoNulo.apply(escolhaAlteracao);
		
		if (escolhaAlteracao.equals(AtributosDisciplina.CODIGO)) {
			Disciplina teste = verificarExistencia(alteracao + d.getCodigo().substring(3, 5));
			if (teste == null)
				d.setCodigo(alteracao);
			else
				throw new RegistroDuplicadoException("Disciplina com o codigo já existe no Banco de Dados.");
		}
		else if (escolhaAlteracao.equals(AtributosDisciplina.NIVEL_DISCIPLINA)) {
			Disciplina teste = new Disciplina(d);
			teste.setNivelEscolar(alteracao);
			teste = verificarExistencia(teste.getCodigo());
			
			if (teste == null)
				d.setNivelEscolar(alteracao);
			else
				throw new RegistroDuplicadoException("Disciplina com o codigo já existe no Banco de Dados.");
		}
		else if (escolhaAlteracao.equals(AtributosDisciplina.NOME)) 
			d.setNome(alteracao);
		
		else if (escolhaAlteracao.equals(AtributosDisciplina.CARGA_HORARIA)) 
			d.setCargaHoraria(Funcionalidades.converterStringPraInteger(alteracao));
		
		mergeAtomico(d);
		return d;
	}	
	
	private Disciplina verificarExistencia(String codigo) {
		return consutlarUm(OBTER_DISCIPLINA_SQL, "codigo", codigo); 
	}
	
}
