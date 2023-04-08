package app.modelo.infra;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.AtributosProfessor;
import app.modelo.Disciplina;
import app.modelo.Funcionalidades;
import app.modelo.Professor;

public class ProfessorDAO extends DAO<Professor>{
	
	static public String obterProfessorSQL = "obterPessoaPorCPF";
	
	public ProfessorDAO() {
		super(Professor.class);
	}
	
	public Professor obterProfessor(String CPF) {
		Professor p = verificarExistencia(CPF);
		return p;
	}
	
	public Professor criarProfessor(Professor p) {
		if (verificarExistencia(p.getCPF()) == null) {
			incluirAtomico(p);
			return p;
		} else {
			throw new RegistroDuplicadoException("O CPF do professor já existe no Banco de Dados.");			
		}
	}
	
	public Professor removerProfessor(String CPF) {
		Professor p = verificarExistencia(CPF);
		
		if (p != null) {
			removerEntidade(p);
			return p;
		} else {
			throw new ConsultaNulaException("Nenhum professor encontrado.");
		}		
	}
	
	public Professor Atualizar(String CPF, AtributosProfessor escolhaAlteracao, String alteracao){
		Professor p = verificarExistencia(CPF);
		Funcionalidades.testarObjetoNulo.apply(p);
		Funcionalidades.testarObjetoNulo.apply(escolhaAlteracao);
		
		if (escolhaAlteracao.equals(AtributosProfessor.CPF)) {
			Professor teste = verificarExistencia(alteracao);
			if (teste == null) 
				p.setCPF(alteracao);				
			else 
				throw new RegistroDuplicadoException("A CPF já existe.");	
		}
		else if (escolhaAlteracao.equals(AtributosProfessor.NOME)) 
			p.setNome(alteracao);
		else if (escolhaAlteracao.equals(AtributosProfessor.SEXO)) 
			p.setSexo(alteracao);
		else if (escolhaAlteracao.equals(AtributosProfessor.EMAIL)) 
			p.setEmail(alteracao);
		else if (escolhaAlteracao.equals(AtributosProfessor.FORMACAO)) 
			p.setFormacao(alteracao);
		else if (escolhaAlteracao.equals(AtributosProfessor.SALARIO)) 
			p.setSalario(Funcionalidades.converterStringPraDouble(alteracao));
		else if (escolhaAlteracao.equals(AtributosProfessor.INICIO_CONTRATO)) 
			p.setInicioContrato(Funcionalidades.cirarDataSQL(alteracao));
		
		mergeAtomico(p);
		return p;
	}	
	
	private Professor verificarExistencia(String CPF) {
		CPF = Funcionalidades.validarCPF(CPF);
		List<Professor> professores = consultar(obterProfessorSQL, "CPF", CPF);
		
		if (professores.size() != 0) 
			return professores.get(0);
		else 
			return null;
	}
	
	private List<Professor> consultar(String nomeConsulta, Object... params){
		TypedQuery<Professor> query = em.createNamedQuery(nomeConsulta, classe);
		for (int i = 0; i < params.length; i+=2) {
			query.setParameter(params[i].toString(), params[i+1]);
		}
		
		return query.getResultList();
	}
	
}
