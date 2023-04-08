package app.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.infra.DAOs;

public class TurmaDAOTteste {
	Turma turm1;
	Turma turm2;
	Turma turm3;
	
	@BeforeEach
	void inicializarDAOeAluno() {
		turm3 = new Turma("fundamental", "Z", "MP65");
		turm2 = new Turma("ensino medio", "Z", "MP65");
		turm1 = new Turma("fundamental", "X", "MP65");
		DAOs.turmDAO.criarTurma(turm3);
		DAOs.turmDAO.criarTurma(turm2);
		DAOs.turmDAO.criarTurma(turm1);
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
		DAOs.turmDAO.criarTurma(new Turma("fundamental", "X", "MP65"));
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
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.turmDAO.removerTurma("DDDD");
		});
	}
	
	@Test
	void testarRemocao4() {
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
	
}
