package app.modelo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.modelo.NivelEscolar;
import app.modelo.Turma;

public class TurmaTeste {
	
	Turma turm;
	static Professor p;
	static Aluno a;
	
	@BeforeAll
	static void inicializarSecundarios() {
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		p = new Professor("Estefani Grilo Aguiar", "93774484090", "Masculino", "estefani@gmail.com", AreasDeConhecimento.LITERATURA, 4550.0, sqlDate);
		a = new Aluno("teste", "24581919088", "Feminino", "teste@gmail.com", sqlDate, null);
	}
	
	@BeforeEach
	void inicializarTurma() {
		turm = new Turma(NivelEscolar.FUNDAMENTAL, "A", "MP65");
	}
	
	@Test
	void alterarNivelEscolar1() {
		turm.setTipoEnsino(NivelEscolar.FUNDAMENTAL);
		assertTrue("Fundamental".equals(turm.getTipoEnsino()));
	}
	
	@Test
	void alterarNivelEscolar2() {
		turm.setTipoEnsino(NivelEscolar.ENSINO_MEDIO);
		assertTrue("Ensino Medio".equals(turm.getTipoEnsino()));
	}
	
	@Test
	void alterarNivelEscolar3() {
		assertThrows(NullPointerException.class, () -> {
			turm.setTipoEnsino(null);
		});
	}
	
	@Test
	void alterarLetraTurma1() {
		turm.setLetraTurma("b");
		assertTrue("B".equals(turm.getLetraTurma()));
	}
	
	@Test
	void alterarLetraTurma2() {
		assertThrows(IllegalArgumentException.class, () -> {
			turm.setLetraTurma("");
		});
	}
	
	@Test
	void alterarLetraTurma3() {
		assertThrows(NullPointerException.class, () -> {
			turm.setLetraTurma(null);
		});
	}
	
	@Test
	void alterarLetraTurma4() {
		assertThrows(IllegalArgumentException.class, () -> {
			turm.setLetraTurma("as");
		});
	}
	
	@Test
	void alterarLetraTurma5() {
		assertThrows(IllegalArgumentException.class, () -> {
			turm.setLetraTurma("1");
		});
	}
	
	@Test
	void alterarSala1() {
		turm.setSala("MP55");
		assertTrue("MP55".equals(turm.getSala()));
	}
	
	@Test
	void alterarSala2() {
		turm.setSala("1");
		assertTrue("1".equals(turm.getSala()));
	}
	
	@Test
	void alterarSala3() {
		assertThrows(IllegalArgumentException.class, () -> {
			turm.setSala("");
		});
	}
	
	@Test
	void alterarSala4() {
		assertThrows(NullPointerException.class, () -> {
			turm.setSala(null);
		});
	}
	
	@Test
	void testarAlunoNaTurma() {
		a.setTurma(turm);
		assertTrue(turm.equals(a.getTurma()));
		a.setTurma(null);
		assertEquals(0, turm.getAlunos().size());
	}
	
	@Test
	void testarProfessorNaTurma() {
		p.adicionarTurma(turm);
		assertTrue(turm.equals(p.getTurmas().get(0)));
		p.removerTurma(turm);
		assertEquals(0, p.getTurmas().size());
	}

	@Test
	void testarCodigo1() {
		assertTrue("EFA".equals(turm.getCodigo()));;
	}
	
	@Test
	void testarCodigo2() {
		turm.setTipoEnsino(NivelEscolar.ENSINO_MEDIO);
		assertTrue("EMA".equals(turm.getCodigo()));;
	}
	
	@Test
	void testarCodigo3() {
		assertTrue("EFA".equals(turm.getCodigo()));;
	}
	
	@Test
	void testarCodigo4() {
		turm.setLetraTurma("B");;
		assertTrue("EFB".equals(turm.getCodigo()));;
	}
	
}
