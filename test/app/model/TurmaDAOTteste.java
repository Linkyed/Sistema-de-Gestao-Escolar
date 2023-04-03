package app.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
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
import app.modelo.infra.DisciplinaDAO;
import app.modelo.infra.TurmaDAO;

public class TurmaDAOTteste {
	TurmaDAO dao;
	Turma turm1;
	Turma turm2;
	Turma turm3;
	
	@BeforeEach
	void inicializarDAOeAluno() {
		dao = new TurmaDAO();
		turm3 = new Turma(NivelEscolar.FUNDAMENTAL, "Z", "MP65");
		turm2 = new Turma(NivelEscolar.ENSINO_MEDIO, "Z", "MP65");
		turm1 = new Turma(NivelEscolar.FUNDAMENTAL, "X", "MP65");
		dao.criarTurma(turm3);
		dao.criarTurma(turm2);
		dao.criarTurma(turm1);
	}
	
	@AfterEach
	void removerAluno() {
		try {
			dao.removerTurma("EFZ");
			dao.removerTurma("EMZ");
			dao.removerTurma("EFX");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	void testarCriacao1() {			
		assertTrue(turm1.equals(dao.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.criarTurma(turm1);				
		});
	}
	
	@Test
	void testarCriacao3() {
		assertThrows(NullPointerException.class, () -> {
			dao.criarTurma(null);				
		});
	}
	
	@Test
	void testarRemocao1() {
		Turma t = dao.removerTurma("EFX");
		boolean verificar = t.equals(turm1);
		dao.criarTurma(new Turma(NivelEscolar.FUNDAMENTAL, "X", "MP65"));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao2() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.removerTurma("DDD");
		});
	}

	@Test
	void testarRemocao3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.removerTurma("DDDD");
		});
	}
	
	@Test
	void testarRemocao4() {
		assertThrows(NullPointerException.class, () -> {
			dao.removerTurma(null);
		});
	}
	
	@Test
	void alteracaoNula1() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar(null, AtributosTurma.NIVEL_TURMA, "ensino medio");
		});
	}
	
	@Test
	void alteracaoNula2() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar("EFX", null, "B");
		});
	}
	
	@Test
	void alteracaoNula3() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar("EFX", AtributosTurma.NIVEL_TURMA, null);
		});
	}
	
	@Test
	void alteracaoVazia() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("EFX", AtributosTurma.NIVEL_TURMA, "");
		});
	}
	
	@Test
	void alteracaoCodigoInvalido() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.Atualizar("DDD", AtributosTurma.NIVEL_TURMA, "fundamental");
		});
	}
	
	@Test
	void alteracaoTipoEnsino1() {
		dao.Atualizar("EFX", AtributosTurma.NIVEL_TURMA, "ensino medio");
		boolean verificacao = "EMX".equals(dao.obterUltimo().getCodigo());
		dao.Atualizar("EMX", AtributosTurma.NIVEL_TURMA, "fundamental");
		assertTrue(verificacao);
	}
	
	@Test
	void alteracaoTipoEnsino2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.Atualizar("EMZ", AtributosTurma.NIVEL_TURMA, "fundamental");
		});
	}
	
	@Test
	void alteracaoLetraTurma1() {
		dao.Atualizar("EFX", AtributosTurma.LETRA_TURMA, "W");
		boolean verificacao = "EFW".equals(dao.obterUltimo().getCodigo());
		dao.Atualizar("EFW", AtributosTurma.LETRA_TURMA, "X");
		assertTrue(verificacao);
	}
	
	@Test
	void alteracaoLetraTurma2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.Atualizar("EFZ", AtributosTurma.LETRA_TURMA, "X");
		});
	}
	
	@Test
	void alteracaoSala1() {
		dao.Atualizar("EFX", AtributosTurma.SALA, "MT44");
		assertTrue("MT44".equals(dao.obterUltimo().getSala()));
	}
	
}
