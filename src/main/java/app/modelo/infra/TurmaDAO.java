package app.modelo.infra;

import java.util.Iterator;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.AtributosAluno;
import app.modelo.AtributosProfessor;
import app.modelo.AtributosTurma;
import app.modelo.Funcionalidades;
import app.modelo.Professor;
import app.modelo.Turma;

public class TurmaDAO extends DAO<Turma>{
	
	public TurmaDAO() {
		super(Turma.class);
	}
	
	public Turma getTurmaPorCodigo(String codigo) {
		codigo = Funcionalidades.testarStringNula
				.andThen(Funcionalidades.testarStringVazia).apply(codigo).toUpperCase();
		if (codigo.length() != 3) throw new IllegalArgumentException("Codigo invalido.");
		
		String jpql = "select e from " + classe.getName() + " e where codigo = '" + codigo + "'";
		try {
			TypedQuery<Turma> query = em.createQuery(jpql, classe);	
			if (query != null) {
				Turma t = query.getSingleResult();
				return t;
			} 
			throw new ConsultaNulaException("A consulta pelo Codigo resultou em nada encontrado.");
		} catch (NoResultException e) {
			throw new ConsultaNulaException("A consulta pelo Codigo resultou em nada encontrado.");
		}
	}
	
	public Turma criarTurma(Turma t) {
		Funcionalidades.testarObjetoNulo.apply(t);
		
		try {
			getTurmaPorCodigo(t.getCodigo());
			throw new RegistroDuplicadoException("O Codigo da turma já existe no Banco de Dados.");
		} catch (ConsultaNulaException e) {
			incluirAtomico(t);
			return t;
		}
	}
	
	public Turma removerTurma(String codigo) {
		Turma t = getTurmaPorCodigo(codigo);
		
		t.getAlunos().forEach(a -> a.setTurma(null));
		t.getProfessores().stream().forEach(p -> DAOs.profDAO.Atualizar(p.getCPF(),
				AtributosProfessor.TURMAS_REMOVER, codigo));
		
		removerEntidade(t);
		return t;
	}
	
	public Turma Atualizar(String codigo, AtributosTurma escolhaAlteracao, String alteracao){
		Turma t = getTurmaPorCodigo(codigo);
		Funcionalidades.testarObjetoNulo.apply(escolhaAlteracao);
		
		if (escolhaAlteracao.equals(AtributosTurma.NIVEL_TURMA)) {
			Turma teste = new Turma(Funcionalidades.StringParaNivelEscolar(alteracao), t.getLetraTurma(), t.getSala());
			try {
				if (getTurmaPorCodigo(teste.getCodigo()) != null) throw new RegistroDuplicadoException("A Turma já existe.");
			} catch (ConsultaNulaException e) {
				t.setTipoEnsino(Funcionalidades.StringParaNivelEscolar(alteracao));
			}			
		}
		else if (escolhaAlteracao.equals(AtributosTurma.LETRA_TURMA)) {
			alteracao = Funcionalidades.testarStringNula
					.andThen(Funcionalidades.testarStringVazia)
					.andThen(Funcionalidades.testarSoLetras).apply(alteracao).toUpperCase();
			if (alteracao.length() != 1) throw new IllegalArgumentException("Letra da turma só pode conter 1 caracter.");
			
			Turma teste = new Turma(Funcionalidades.StringParaNivelEscolar(t.getTipoEnsino()), alteracao, t.getSala());
			try {
				if (getTurmaPorCodigo(teste.getCodigo()) != null) throw new RegistroDuplicadoException("A Turma já existe.");
			} catch (ConsultaNulaException e) {
				t.setLetraTurma(alteracao);
			}	
		}
		else if (escolhaAlteracao.equals(AtributosTurma.SALA)) 
			t.setSala(alteracao);
		
		mergeAtomico(t);
		return t;
	}
}
