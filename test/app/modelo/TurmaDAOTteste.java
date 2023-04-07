package app.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.AreasDeConhecimento;
import app.modelo.AtributosDisciplina;
import app.modelo.AtributosTurma;
import app.modelo.Disciplina;
import app.modelo.NivelEscolar;
import app.modelo.Turma;
import app.modelo.infra.DAOs;
import app.modelo.infra.DisciplinaDAO;
import app.modelo.infra.TurmaDAO;

public class TurmaDAOTteste {
	Turma turm1;
	Turma turm2;
	Turma turm3;
	
	@BeforeAll
	static void inicializarSecundarios() {
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		Professor p = new Professor("Estefani Grilo Aguiar", "66481727030", "Masculino", "estefanixxaads@gmail.com", AreasDeConhecimento.LITERATURA, 4550.0, sqlDate);
		Aluno a = new Aluno("teste", "66481727030", "Feminino", "teste@gmail.com", sqlDate, null);
		DAOs.alunDAO.criarAluno(a);
		DAOs.profDAO.criarProfessor(p);
	}
	
	@BeforeEach
	void inicializarDAOeAluno() {
		turm3 = new Turma(NivelEscolar.FUNDAMENTAL, "Z", "MP65");
		turm2 = new Turma(NivelEscolar.ENSINO_MEDIO, "Z", "MP65");
		turm1 = new Turma(NivelEscolar.FUNDAMENTAL, "X", "MP65");
		DAOs.turmDAO.criarTurma(turm3);
		DAOs.turmDAO.criarTurma(turm2);
		DAOs.turmDAO.criarTurma(turm1);
	}
	
	@AfterAll
	static void removerSecundarios() {
		DAOs.alunDAO.removerAluno("66481727030");
		DAOs.profDAO.removerProfessor("66481727030");
	}
	
	@AfterEach
	void removerAluno() {
		DAOs.turmDAO.removerTurma("EFZ");
		DAOs.turmDAO.removerTurma("EMZ");
		DAOs.turmDAO.removerTurma("EFX");
	}
	
	@Test
	void testarCriacao1() {			
		assertTrue(turm1.equals(DAOs.turmDAO.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.turmDAO.criarTurma(turm1);				
		});
	}
	
	@Test
	void testarCriacao3() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.turmDAO.criarTurma(null);				
		});
	}
	
	@Test
	void testarRemocao1() {
		Turma t = DAOs.turmDAO.removerTurma("EFX");
		boolean verificar = t.equals(turm1);
		DAOs.turmDAO.criarTurma(new Turma(NivelEscolar.FUNDAMENTAL, "X", "MP65"));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao2() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.turmDAO.removerTurma("DDD");
		});
	}

	@Test
	void testarRemocao3() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.turmDAO.removerTurma("DDDD");
		});
	}
	
	@Test
	void testarRemocao4() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.turmDAO.removerTurma(null);
		});
	}
	
	@Test
	void alteracaoNula1() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.turmDAO.Atualizar(null, AtributosTurma.NIVEL_TURMA, "ensino medio");
		});
	}
	
	@Test
	void alteracaoNula2() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.turmDAO.Atualizar("EFX", null, "B");
		});
	}
	
	@Test
	void alteracaoNula3() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.turmDAO.Atualizar("EFX", AtributosTurma.NIVEL_TURMA, null);
		});
	}
	
	@Test
	void alteracaoVazia() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.turmDAO.Atualizar("EFX", AtributosTurma.NIVEL_TURMA, "");
		});
	}
	
	@Test
	void alteracaoCodigoInvalido() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.turmDAO.Atualizar("DDD", AtributosTurma.NIVEL_TURMA, "fundamental");
		});
	}
	
	@Test
	void alteracaoTipoEnsino1() {
		DAOs.turmDAO.Atualizar("EFX", AtributosTurma.NIVEL_TURMA, "ensino medio");
		boolean verificacao = "EMX".equals(DAOs.turmDAO.obterUltimo().getCodigo());
		DAOs.turmDAO.Atualizar("EMX", AtributosTurma.NIVEL_TURMA, "fundamental");
		assertTrue(verificacao);
	}
	
	@Test
	void alteracaoTipoEnsino2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.turmDAO.Atualizar("EMZ", AtributosTurma.NIVEL_TURMA, "fundamental");
		});
	}
	
	@Test
	void alteracaoLetraTurma1() {
		DAOs.turmDAO.Atualizar("EFX", AtributosTurma.LETRA_TURMA, "W");
		boolean verificacao = "EFW".equals(DAOs.turmDAO.obterUltimo().getCodigo());
		DAOs.turmDAO.Atualizar("EFW", AtributosTurma.LETRA_TURMA, "X");
		assertTrue(verificacao);
	}
	
	@Test
	void alteracaoLetraTurma2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.turmDAO.Atualizar("EFZ", AtributosTurma.LETRA_TURMA, "X");
		});
	}
	
	@Test
	void alteracaoSala1() {
		DAOs.turmDAO.Atualizar("EFX", AtributosTurma.SALA, "MT44");
		assertTrue("MT44".equals(DAOs.turmDAO.obterUltimo().getSala()));
	}
	
	@Test
	void testarProfessorNaTurma() {
		DAOs.profDAO.Atualizar("66481727030", AtributosProfessor.TURMAS_ADICIONAR, "EFX");
		assertTrue(turm1.equals(DAOs.profDAO.getProfessorPorCPF("66481727030").getTurmas().get(0)));
	}
	
	@Test
	void testarAlunoNaTurma() {
		DAOs.alunDAO.Atualizar("66481727030", AtributosAluno.TURMA, "EFX");
		assertTrue(turm1.equals(DAOs.alunDAO.getAlunoPorCPF("66481727030").getTurma()));
	}
	
}
