package app.modelo.infra;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.AtributosAluno;
import app.modelo.Funcionalidades;
import app.modelo.Professor;
import app.modelo.Turma;

public class AlunoDAO extends DAO<Aluno> {
	
	static final String OBTER_ALUNO_SQL = "obterAlunoPorCPF";
	
	public AlunoDAO() {
		super(Aluno.class);
	}
	
	public Aluno obterAluno(String CPF) {
		Aluno a = verificarExistencia(CPF);
		if (a == null) throw new ConsultaNulaException("Nenhum aluno encontrado.");
		
		return a;
	}

	public Aluno criarAluno(Aluno a) {
		if (verificarExistencia(a.getCPF()) == null) {
			incluirAtomico(a);
			return a;
		} else {
			throw new RegistroDuplicadoException("O CPF do aluno já existe no Banco de Dados.");			
		}
	}
	
	public Aluno removerAluno(String CPF) {
		Aluno a = verificarExistencia(CPF);
		
		if (a != null) {
			removerEntidade(a);
			return a;
		} else {
			throw new ConsultaNulaException("Nenhum aluno encontrado para ser excluido.");
		}				
	}
	
	public Aluno Atualizar(String CPF, AtributosAluno escolhaAlteracao, String alteracao){
		Aluno a = verificarExistencia(CPF);
		Funcionalidades.testarObjetoNulo.apply(a);
		Funcionalidades.testarObjetoNulo.apply(escolhaAlteracao);
		
		if (escolhaAlteracao.equals(AtributosAluno.CPF)) {
			Aluno teste = verificarExistencia(alteracao);
			if (teste == null) 
				a.setCPF(alteracao);				
			else 
				throw new RegistroDuplicadoException("A CPF já existe.");			
		}
		else if (escolhaAlteracao.equals(AtributosAluno.TURMA)) {
			if (alteracao == null) a.setTurma(null);
			else {
				Turma t = DAOs.turmDAO.obterTurma(alteracao);
				a.setTurma(t);
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
	
	private Aluno verificarExistencia(String CPF) {
		return consutlarUm(OBTER_ALUNO_SQL, "CPF", CPF); 
	}
	
}
