package app.modelo.infra;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.AtributosAluno;
import app.modelo.AtributosDisciplina;
import app.modelo.Disciplina;
import app.modelo.Funcionalidades;

public class DisciplinaDAO extends DAO<Disciplina> {
	
	public DisciplinaDAO() {
		super(Disciplina.class);
	}
	
	public Disciplina getDisciplinaPorCodigo(String codigo) {
		if (codigo == null) throw new NullPointerException("Objeto String Codigo nulo.");
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
		if (d == null) throw new NullPointerException("Objeto Disciplina nulo.");
		try {
			getDisciplinaPorCodigo(d.getCodigo());
			throw new RegistroDuplicadoException("O Codigo da disciplina já existe no Banco de Dados.");
		} catch (ConsultaNulaException e) {
			incluirAtomico(d);
			return d;
		}
	}
	
	public Disciplina removerAluno(String codigo) {
		Disciplina d = getDisciplinaPorCodigo(codigo);
		if (d != null) {
			removerEntidade(d);
			return d;			
		} else {
			return null;
		}
	}
	
	public Disciplina Atualizar(String codigo, AtributosDisciplina escolhaAlteracao, String alteracao){
		if (codigo == null) throw new NullPointerException("Objeto String Codigo nulo.");
		if (alteracao == null) throw new NullPointerException("Objeto String alteracao nulo.");
		if (escolhaAlteracao == null) throw new NullPointerException("Objeto AtributosDisciplina escolhaAlteracao nulo.");
		
		Disciplina d = getDisciplinaPorCodigo(codigo);
		if (escolhaAlteracao.equals(AtributosDisciplina.NOME)) {
			Disciplina teste = new Disciplina(Disciplina.StringParaAreaConhecimento(alteracao), d.getCargaHoraria(), Disciplina.StringParaNivelEscolar(d.getNivelDaDisciplina()));
			try {
				if (getDisciplinaPorCodigo(teste.getCodigo()) != null) throw new RegistroDuplicadoException("A Disciplina já existe.");
			} catch (ConsultaNulaException e) {
				d.setNome(Disciplina.StringParaAreaConhecimento(alteracao));
			}			
		}
		else if (escolhaAlteracao.equals(AtributosDisciplina.NIVEL_DISCIPLINA)) {
			Disciplina teste = new Disciplina(Disciplina.StringParaAreaConhecimento(d.getNome()), d.getCargaHoraria(), Disciplina.StringParaNivelEscolar(alteracao));
			try {
				if (getDisciplinaPorCodigo(teste.getCodigo()) != null) throw new RegistroDuplicadoException("A Disciplina já existe.");
			} catch (ConsultaNulaException e) {
				d.setNivelDaDisciplina(Disciplina.StringParaNivelEscolar(alteracao));
			}
		}
		else if (escolhaAlteracao.equals(AtributosDisciplina.CARGA_HORARIA)) 
			d.setCargaHoraria(Funcionalidades.converterStringPraInteger(alteracao));
		
		mergeAtomico(d);
		return d;
	}	
	
}
