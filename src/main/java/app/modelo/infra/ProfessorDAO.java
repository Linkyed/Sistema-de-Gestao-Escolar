package app.modelo.infra;

import javax.persistence.NoResultException;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.Professor;

public class ProfessorDAO extends DAO<Professor>{
	public ProfessorDAO() {
		super(Professor.class);
	}
	
	public Professor getProfessorPorCPF(String CPF) {
		if (CPF == null) throw new NullPointerException("Objeto CPF nulo.");
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
	
}
