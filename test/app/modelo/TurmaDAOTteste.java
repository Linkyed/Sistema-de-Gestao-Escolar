package app.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.infra.DAOs;

public class TurmaDAOTteste {
	Turma turm1;
	Turma turm2;
	Turma turm3;
	
	@BeforeAll
	static void inicializarAluno() {
		DAOs.alunDAO.criarAluno(new Aluno("teste", "32518682090", "Feminino", "teste@gmail.com", 
				Funcionalidades.cirarDataSQL("28-02-2023")));
	}
	
	@BeforeEach
	void inicializarTurma() {
		turm3 = new Turma("fundamental", "Z", "MP65");
		turm2 = new Turma("ensino medio", "Z", "MP65");
		turm1 = new Turma("fundamental", "X", "MP65");
		DAOs.turmDAO.criarTurma(turm3);
		DAOs.turmDAO.criarTurma(turm2);
		DAOs.turmDAO.criarTurma(turm1);
	}
	
	@AfterEach
	void removerTurma() {
		DAOs.turmDAO.removerTurma("EFZ");
		DAOs.turmDAO.removerTurma("EMZ");
		DAOs.turmDAO.removerTurma("EFX");
	}
	
	@AfterAll
	static void removerAluno() {
		DAOs.alunDAO.removerAluno("32518682090");
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
		DAOs.turmDAO.criarTurma(new Turma("fundamental", "X", "MP65"));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao2() {
		DAOs.alunDAO.Atualizar("32518682090", AtributosAluno.TURMA, "EFX");
		Turma t = DAOs.turmDAO.removerTurma("EFX");
		boolean verificar1 = t.equals(turm1);
		boolean verificar2 = DAOs.alunDAO.obterAluno("32518682090").getTurma() == null;
		DAOs.turmDAO.criarTurma(new Turma("fundamental", "X", "MP65"));
		assertTrue(verificar1 && verificar2);
	}
	
	@Test
	void testarRemocao3() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.turmDAO.removerTurma("DDD");
		});
	}

	@Test
	void testarRemocao4() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.turmDAO.removerTurma("DDDD");
		});
	}
	
	@Test
	void testarRemocao5() {
		assertThrows(ConsultaNulaException.class, () -> {
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
	void alteracaoVazia1() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.turmDAO.Atualizar("EFX", AtributosTurma.NIVEL_TURMA, "");
		});
	}
	
	@Test
	void alteracaoVazia2() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.turmDAO.Atualizar("", AtributosTurma.NIVEL_TURMA, "XDD");
		});
	}
	
	@Test
	void alteracaoCodigoInvalido() {
		assertThrows(NullPointerException.class, () -> {
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
	void verificarAlunos1() {
		DAOs.alunDAO.Atualizar("32518682090", AtributosAluno.TURMA, "EFX");
		assertEquals(1, DAOs.turmDAO.obterTurma("EFX").getAlunos().size());
		DAOs.alunDAO.Atualizar("32518682090", AtributosAluno.TURMA, null);
	}
	
	@Test
	void verificarAlunos2() {
		DAOs.alunDAO.Atualizar("32518682090", AtributosAluno.TURMA, "EFX");
		DAOs.alunDAO.Atualizar("32518682090", AtributosAluno.TURMA, null);
		assertEquals(0, DAOs.turmDAO.obterTurma("EFX").getAlunos().size());
	}
	
	@Test
	void verificarAlunos3() {
		DAOs.alunDAO.Atualizar("32518682090", AtributosAluno.TURMA, "EFX");
		DAOs.alunDAO.Atualizar("32518682090", AtributosAluno.TURMA, "EFZ");
		assertEquals(0, DAOs.turmDAO.obterTurma("EFX").getAlunos().size());
		assertEquals(1, DAOs.turmDAO.obterTurma("EFZ").getAlunos().size());
		DAOs.alunDAO.Atualizar("32518682090", AtributosAluno.TURMA, null);
	}
	
}
