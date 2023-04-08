package app.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.modelo.AreasDeConhecimento;
import app.modelo.Disciplina;
import app.modelo.NivelEscolar;

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
		dis.setNivelDaDisciplina("ensino medio");
		assertEquals("Ensino Medio", dis.getNivelDaDisciplina());
	}
	
	@Test
	void alterarNivelDaDisciplina2() {
		dis.setNivelDaDisciplina("fundamental");
		assertEquals("Fundamental", dis.getNivelDaDisciplina());
	}
	
	@Test
	void alterarNivelDaDisciplina3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelDaDisciplina(" ");
		});
	}
	
	@Test
	void alterarNivelDaDisciplina4() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelDaDisciplina("nada aveere");
		});
	}
	
	@Test
	void alterarNivelDaDisciplina5() {
		assertThrows(NullPointerException.class, () -> {
			dis.setNivelDaDisciplina(null);
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
			dis.setNivelDaDisciplina("GE01");
		});
	}
	
	@Test
	void alterarCodigo3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelDaDisciplina(" ");
		});
	}
	
	@Test
	void alterarCodigo4() {
		assertThrows(IllegalArgumentException.class, () -> {
			dis.setNivelDaDisciplina("GE");
		});
	}
	
	@Test
	void alterarCodigo5() {
		assertThrows(NullPointerException.class, () -> {
			dis.setNivelDaDisciplina(null);
		});
	}
	
}
