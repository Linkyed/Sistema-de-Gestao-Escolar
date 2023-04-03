package app.modelo.infra;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.AtributosAluno;
import app.modelo.Funcionalidades;

public class AlunoDAO extends DAO<Aluno> {
	
	public AlunoDAO() {
		super(Aluno.class);
	}
	
	public Aluno getAlunoPorCPF(String CPF) {
		CPF = Funcionalidades.verificarValidadeCPF(CPF);
		String jpql = "select e from " + classe.getName() + " e where CPF = " + CPF;
		try {
			TypedQuery<Aluno> query = em.createQuery(jpql, classe);	
			if (query != null) {
				Aluno a = query.getSingleResult();
				return a;
			} 
			throw new ConsultaNulaException("A consulta pelo CPF resultou em nada encontrado.");
		} catch (NoResultException e) {
			throw new ConsultaNulaException("A consulta pelo CPF resultou em nada encontrado.");
		}
	}
	
	public Aluno getAlunoPorMatricula(String matricula) {
		matricula = Funcionalidades.testarStringNula
				.andThen(Funcionalidades.testarStringVazia).apply(matricula);
		if (matricula.length() != 14) throw new IllegalArgumentException("Matricula invalida.");
		
		String jpql = "select e from " + classe.getName() + " e where matricula = " + matricula;
		try {
			Aluno a = em.createQuery(jpql, classe).getSingleResult();			
			return a;
		} catch (NoResultException e) {
			throw new ConsultaNulaException("A consulta pela Matricula resultou em nada encontrado.");
		}
	}
	
	public Aluno criarAluno(Aluno a) {
		Funcionalidades.testarObjetoNulo.apply(a);
		try {
			getAlunoPorCPF(a.getCPF());
			getAlunoPorMatricula(a.getMatricula());
			throw new RegistroDuplicadoException("O CPF ou Matricula do aluno já existe no Banco de Dados.");
		} catch (ConsultaNulaException e) {
			incluirAtomico(a);
			return a;
		}
	}
	
	public Aluno removerAluno(String CPF) {
		Aluno a = getAlunoPorCPF(CPF);
		removerEntidade(a);
		return a;			
	}
	
	public Aluno Atualizar(String CPF, AtributosAluno escolhaAlteracao, String alteracao){
		Aluno a = getAlunoPorCPF(CPF);
		Funcionalidades.testarObjetoNulo.apply(escolhaAlteracao);
		alteracao = Funcionalidades.testarStringNula
				.andThen(Funcionalidades.testarStringVazia).apply(alteracao);
		
		if (escolhaAlteracao.equals(AtributosAluno.CPF)) {
			try {
				if (getAlunoPorCPF(alteracao) != null) throw new RegistroDuplicadoException("A CPF já existe.");
			} catch (ConsultaNulaException e) {
				a.setCPF(alteracao);
			}			
		}
		else if (escolhaAlteracao.equals(AtributosAluno.NOME)) 
			a.setNome(alteracao);
		else if (escolhaAlteracao.equals(AtributosAluno.SEXO)) 
			a.setSexo(alteracao);
		else if (escolhaAlteracao.equals(AtributosAluno.EMAIL_DO_RESPONSAVEL))
			a.setEmail(alteracao);
		else if (escolhaAlteracao.equals(AtributosAluno.DATA_NASCIMENTO)) 
			a.setDataNascimento(Funcionalidades.cirarDataSQL(alteracao));
		
		mergeAtomico(a);
		return a;
	}	
	
}
