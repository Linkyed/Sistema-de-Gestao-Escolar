package app.modelo.infra;

import javax.persistence.NoResultException;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.AtributosProfessor;
import app.modelo.Disciplina;
import app.modelo.Funcionalidades;
import app.modelo.Professor;

public class ProfessorDAO extends DAO<Professor>{
	public ProfessorDAO() {
		super(Professor.class);
	}
	
	public Professor getProfessorPorEmail(String email) {
		email = Funcionalidades.verificarEmail(email);
		String jpql = "select e from " + classe.getName() + " e where email = '" + email + "'";
		try {
			Professor p = em.createQuery(jpql, classe).getSingleResult();	
			return p;
		} catch (NoResultException e) {
			throw new ConsultaNulaException("A consulta pelo Email resultou em nada encontrado.");
		}
	}
	
	public Professor getProfessorPorCPF(String CPF) {
		CPF = Funcionalidades.verificarValidadeCPF(CPF);
		String jpql = "select e from " + classe.getName() + " e where CPF = " + CPF;
		try {
			Professor p = em.createQuery(jpql, classe).getSingleResult();			
			return p;
		} catch (NoResultException e) {
			throw new ConsultaNulaException("A consulta pelo CPF resultou em nada encontrado.");
		}
	}
	
	public Professor criarProfessor(Professor p) {
		Funcionalidades.testarObjetoNulo.apply(p);
		try {
			getProfessorPorCPF(p.getCPF());
			getProfessorPorEmail(p.getEmail());
			throw new RegistroDuplicadoException("O CPF ou Email do professor já existe no Banco de Dados.");
		} catch (ConsultaNulaException e) {
			incluirAtomico(p);
			return p;
		}
	}
	
	public Professor removerProfessor(String CPF) {
		Professor p = getProfessorPorCPF(CPF);
		removerEntidade(p);
		return p;			
	}
	
	public Professor Atualizar(String CPF, AtributosProfessor escolhaAlteracao, String alteracao){
		Professor p = getProfessorPorCPF(CPF);
		Funcionalidades.testarObjetoNulo.apply(escolhaAlteracao);
		alteracao = Funcionalidades.testarStringNula
				.andThen(Funcionalidades.testarStringVazia).apply(alteracao);
		
		if (escolhaAlteracao.equals(AtributosProfessor.NOME)) 
			p.setNome(alteracao);
		else if (escolhaAlteracao.equals(AtributosProfessor.CPF)) {
			try {
				if (getProfessorPorCPF(alteracao) != null) throw new RegistroDuplicadoException("A CPF já existe.");
			} catch (ConsultaNulaException e) {
				p.setCPF(alteracao);
			}			
		}
		else if (escolhaAlteracao.equals(AtributosProfessor.SEXO)) 
			p.setSexo(alteracao);
		else if (escolhaAlteracao.equals(AtributosProfessor.EMAIL)) {
			try {
				if (getProfessorPorEmail(alteracao) != null) throw new RegistroDuplicadoException("O Email já existe.");
			} catch (ConsultaNulaException e) {			
				p.setEmail(alteracao);
			}
		}
		else if (escolhaAlteracao.equals(AtributosProfessor.FORMACAO)) 
			p.setAreaDeFormacao(Funcionalidades.stringParaAreaConhecimento(alteracao));
		else if (escolhaAlteracao.equals(AtributosProfessor.SALARIO)) 
			p.setSalario(Funcionalidades.converterStringPraDouble(alteracao));
		else if (escolhaAlteracao.equals(AtributosProfessor.INICIO_CONTRATO)) 
			p.setInicioContrato(Funcionalidades.cirarDataSQL(alteracao));
		else if (escolhaAlteracao.equals(AtributosProfessor.DISCIPLINAS_ADICIONAR)) 
			p.adicionarDisciplinas(new DisciplinaDAO().getDisciplinaPorCodigo(alteracao));
		else if (escolhaAlteracao.equals(AtributosProfessor.DISCIPLINAS_REMOVER)) 
			p.getDisciplinas().remove(new DisciplinaDAO().getDisciplinaPorCodigo(alteracao));
		
		mergeAtomico(p);
		return p;
	}	
	
}
