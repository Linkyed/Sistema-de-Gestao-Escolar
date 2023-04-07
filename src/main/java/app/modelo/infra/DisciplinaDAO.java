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
	
	public DisciplinaDAO() {
		super(Disciplina.class);
	}
	
	public Disciplina getDisciplinaPorCodigo(String codigo) {
		codigo = Funcionalidades.testarStringNula
				.andThen(Funcionalidades.testarStringVazia).apply(codigo);
		if (codigo.length() != 5) throw new IllegalArgumentException("Codigo invalido.");
		
		String jpql = "select e from " + classe.getName() + " e where codigo = '" + codigo + "'";
		try {
			TypedQuery<Disciplina> query = em.createQuery(jpql, classe);	
			if (query != null) {
				Disciplina d = query.getSingleResult();
				return d;
			} 
			throw new ConsultaNulaException("A consulta pelo Codigo resultou em nada encontrado.");
		} catch (NoResultException e) {
			throw new ConsultaNulaException("A consulta pelo Codigo resultou em nada encontrado.");
		}
	}
	
	public Disciplina criarDisciplina(Disciplina d) {
		Funcionalidades.testarObjetoNulo.apply(d);
		
		try {
			getDisciplinaPorCodigo(d.getCodigo());
			throw new RegistroDuplicadoException("O Codigo da disciplina já existe no Banco de Dados.");
		} catch (ConsultaNulaException e) {
			incluirAtomico(d);
			return d;
		}
	}
	
	public Disciplina removerDisciplina(String codigo) {
		Disciplina d = getDisciplinaPorCodigo(codigo);
		for (int i = 0; i < d.getProfessores().size(); i ++) {
			DAOs.profDAO.Atualizar(d.getProfessores().get(i).getCPF(), AtributosProfessor.DISCIPLINAS_REMOVER, codigo);
		}
		removerEntidade(d);
		return d;			
	}
	
	public Disciplina Atualizar(String codigo, AtributosDisciplina escolhaAlteracao, String alteracao){
		Disciplina d = getDisciplinaPorCodigo(codigo);
		Funcionalidades.testarObjetoNulo.apply(escolhaAlteracao);
		
		if (escolhaAlteracao.equals(AtributosDisciplina.NOME)) {
			Disciplina teste = new Disciplina(Funcionalidades.stringParaAreaConhecimento(alteracao), d.getCargaHoraria(), Funcionalidades.StringParaNivelEscolar(d.getNivelDaDisciplina()));
			try {
				if (getDisciplinaPorCodigo(teste.getCodigo()) != null) throw new RegistroDuplicadoException("A Disciplina já existe.");
			} catch (ConsultaNulaException e) {
				d.setNome(Funcionalidades.stringParaAreaConhecimento(alteracao));
			}			
		}
		else if (escolhaAlteracao.equals(AtributosDisciplina.NIVEL_DISCIPLINA)) {
			Disciplina teste = new Disciplina(Funcionalidades.stringParaAreaConhecimento(d.getNome()), d.getCargaHoraria(), Funcionalidades.StringParaNivelEscolar(alteracao));
			try {
				if (getDisciplinaPorCodigo(teste.getCodigo()) != null) throw new RegistroDuplicadoException("A Disciplina já existe.");
			} catch (ConsultaNulaException e) {
				d.setNivelDaDisciplina(Funcionalidades.StringParaNivelEscolar(alteracao));
			}
		}
		else if (escolhaAlteracao.equals(AtributosDisciplina.CARGA_HORARIA)) 
			d.setCargaHoraria(Funcionalidades.converterStringPraInteger(alteracao));
		
		mergeAtomico(d);
		return d;
	}	
	
}
