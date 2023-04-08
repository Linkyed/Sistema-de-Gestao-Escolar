package app.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DisciplinaTeste {
	
	Disciplina dis;
	
	@BeforeEach
	void iniciarDisciplina() {
		dis = new Disciplina("artes", 100, "fundamental", "ART");
	}
	
	@Test
	void alterarNome1() {
		dis.setNome("geografia    ");
		assertEquals("Geografia", dis.getNome());
	}
	
	@Test
	void alterarNome2() {
		dis.setNome("   ARTES   ");
		assertEquals("Artes", dis.getNome());
	}
	
	@Test
	void alterarNome3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNome("");
		});
	}
	
	@Test
	void alterarNome4() {
		assertThrows(NullPointerException.class, () -> {
			dis.setNome(null);
		});
	}
	
	@Test
	void alterarCargaHoraria1() {
		dis.setCargaHoraria(120);
		assertEquals(120, dis.getCargaHoraria());
	}
	
	@Test
	void alterarCargaHoraria2() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setCargaHoraria(170);
		});
	}
	
	@Test
	void alterarCargaHoraria3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setCargaHoraria(-20);
		});
	}
	
	@Test
	void alterarCargaHoraria4() {
		assertThrows(NullPointerException.class, () -> {
			dis.setCargaHoraria(null);
		});
	}
	
	@Test
	void alterarNivelDaDisciplina1() {
		dis.setNivelEscolar("ensino medio");
		assertEquals("Ensino Medio", dis.getNivelEscolar());
	}
	
	@Test
	void alterarNivelDaDisciplina2() {
		dis.setNivelEscolar("fundamental");
		assertEquals("Fundamental", dis.getNivelEscolar());
	}
	
	@Test
	void alterarNivelDaDisciplina3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelEscolar(" ");
		});
	}
	
	@Test
	void alterarNivelDaDisciplina4() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelEscolar("nada aveere");
		});
	}
	
	@Test
	void alterarNivelDaDisciplina5() {
		assertThrows(NullPointerException.class, () -> {
			dis.setNivelEscolar(null);
		});
	}
	
	@Test
	void alterarCodigo1() {
		dis.setCodigo("GEO");
		assertTrue("GEO01".equals(dis.getCodigo()));
	}
	
	@Test
	void alterarCodigo2() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelEscolar("GE01");
		});
	}
	
	@Test
	void alterarCodigo3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelEscolar(" ");
		});
	}
	
	@Test
	void alterarCodigo4() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelEscolar("GE");
		});
	}
	
	@Test
	void alterarCodigo5() {
		assertThrows(NullPointerException.class, () -> {
			dis.setNivelEscolar(null);
		});
	}
	
}
