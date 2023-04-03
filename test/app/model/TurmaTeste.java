package app.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.modelo.NivelEscolar;
import app.modelo.Turma;

public class TurmaTeste {
	
	Turma turm;
	
	@BeforeEach
	void inicializarTurma() {
		turm = new Turma(NivelEscolar.FUNDAMENTAL, "A", "MP65");
	}
	
	@Test
	void alterarNivelEscolar1() {
		turm.setTipoEnsino(NivelEscolar.FUNDAMENTAL);
		assertTrue(NivelEscolar.FUNDAMENTAL.equals(turm.getTipoEnsino()));
	}
	
	@Test
	void alterarNivelEscolar2() {
		turm.setTipoEnsino(NivelEscolar.ENSINO_MEDIO);
		assertTrue(NivelEscolar.ENSINO_MEDIO.equals(turm.getTipoEnsino()));
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
