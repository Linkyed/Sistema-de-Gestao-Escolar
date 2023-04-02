package app.modelo.infra;

import javax.management.RuntimeErrorException;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.AtributosProfessor;
import app.modelo.Funcionalidades;
import app.modelo.Professor;

public class ProfessorDAO extends DAO<Professor>{
	public ProfessorDAO() {
		super(Professor.class);
	}
	
	public Professor getProfessorPorEmail(String email) {
		if (email == null) throw new NullPointerException("Objeto String Email nulo.");
		if (!Funcionalidades.verificarEmail(email)) {
			throw new IllegalArgumentException("Email invalido.");
		}
		String jpql = "select e from " + classe.getName() + " e where email = '" + email + "'";
		try {
			Professor p = em.createQuery(jpql, classe).getSingleResult();	
			return p;
		} catch (NoResultException e) {
			throw new ConsultaNulaException("A consulta pelo Email resultou em nada encontrado.");
		}
	}
	
	public Professor getProfessorPorCPF(String CPF) {
		if (CPF == null) throw new NullPointerException("Objeto String CPF nulo.");
		CPF = Funcionalidades.verificarValidadeCPF(CPF);
		String jpql = "select e from " + classe.getName() + " e where CPF = " + CPF;
		try {
			Professor p = em.createQuery(jpql, classe).getSingleResult();			
			return p;
		} catch (NoResultException e) {
			throw new ConsultaNulaException("A consulta pelo CPF resultou em nada encontrado.");
		}
	}
	
	public Professor removerProfessor(String CPF) {
		Professor p = getProfessorPorCPF(CPF);
		if (p != null) {
			removerEntidade(p);
			return p;			
		} else {
			return null;
		}
	}
	
	public Professor criarProfessor(Professor p) {
		if (p == null) throw new NullPointerException("Objeto Professor nulo.");
		try {
			getProfessorPorCPF(p.getCPF());
			throw new RegistroDuplicadoException("O CPF do professor já existe no Banco de Dados.");
		} catch (ConsultaNulaException e) {
			incluirAtomico(p);
			return p;
		}
	}
	
	public Professor Atualizar(String CPF, AtributosProfessor escolhaAlteracao, String alteracao){
		if (CPF == null) throw new NullPointerException("Objeto String CPF nulo.");
		if (alteracao == null) throw new NullPointerException("Objeto String alteracao nulo.");
		if (escolhaAlteracao == null) throw new NullPointerException("Objeto AtributosProfessor escolhaAlteracao nulo.");
		
		Professor p = getProfessorPorCPF(CPF);
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
			p.setAreaDeFormacao(Professor.formacaoString(alteracao));
		else if (escolhaAlteracao.equals(AtributosProfessor.SALARIO)) 
			p.setSalario(Funcionalidades.converterStringPraDouble(alteracao));
		else if (escolhaAlteracao.equals(AtributosProfessor.INICIO_CONTRATO)) 
			p.setInicioContrato(Funcionalidades.cirarDataSQL(alteracao));
		
		mergeAtomico(p);
		return p;
	}	
	
}
