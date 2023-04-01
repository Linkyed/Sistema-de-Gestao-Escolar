package app.model;

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
		dis = new Disciplina(AreasDeConhecimento.ARTES, 100, NivelEscolar.FUNDAMENTAL);
	}
	
	@Test
	void alterarNome1() {
		dis.setNome(AreasDeConhecimento.GEOGRAFIA);
		assertEquals("Geografia", dis.getNome());
	}
	
	@Test
	void alterarNome2() {
		dis.setNome(AreasDeConhecimento.ARTES);
		assertEquals("Artes", dis.getNome());
	}
	
	@Test
	void alterarNome3() {
		dis.setNome(AreasDeConhecimento.BIOLOGIA);
		assertEquals("Biologia", dis.getNome());
	}
	
	@Test
	void alterarNome4() {
		dis.setNome(AreasDeConhecimento.EDUCACAO_FISICA);
		assertEquals("Educação Física", dis.getNome());
	}
	
	@Test
	void alterarNome5() {
		dis.setNome(AreasDeConhecimento.FILOSOFIA);
		assertEquals("Filosofia", dis.getNome());
	}
	
	@Test
	void alterarNome6() {
		dis.setNome(AreasDeConhecimento.HISTORIA);
		assertEquals("História", dis.getNome());
	}
	
	@Test
	void alterarNome7() {
		dis.setNome(AreasDeConhecimento.MATEMATICA);
		assertEquals("Matemática", dis.getNome());
	}
	
	@Test
	void alterarNome8() {
		dis.setNome(AreasDeConhecimento.FISICA);
		assertEquals("Física", dis.getNome());
	}
	
	@Test
	void alterarNome9() {
		dis.setNome(AreasDeConhecimento.QUIMICA);
		assertEquals("Química", dis.getNome());
	}
	
	@Test
	void alterarNome10() {
		dis.setNome(AreasDeConhecimento.SOCIOLOGIA);
		assertEquals("Sociologia", dis.getNome());
	}
	
	@Test
	void alterarNome11() {
		dis.setNome(AreasDeConhecimento.LINGUA_PORTUGUESA);
		assertEquals("Língua Portuguesa", dis.getNome());
	}
	
	@Test
	void alterarNome12() {
		dis.setNome(AreasDeConhecimento.LITERATURA);
		assertEquals("Literatura", dis.getNome());
	}
	
	@Test
	void alterarNome13() {
		dis.setNome(AreasDeConhecimento.LINGUA_INGLESA);
		assertEquals("Língua Inglesa", dis.getNome());
	}
	
	@Test
	void alterarNome14() {
		dis.setNome(AreasDeConhecimento.LINGUA_ALEMA);
		assertEquals("Língua Alemã", dis.getNome());
	}
	
	@Test
	void alterarNome15() {
		dis.setNome(AreasDeConhecimento.LINGUA_FRANCESA);
		assertEquals("Língua Francesa", dis.getNome());
	}
	
	@Test
	void alterarNome16() {
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
		dis.setNivelDaDisciplina(NivelEscolar.ENSINO_MEDIO);
		assertEquals("Ensino Medio", dis.getNivelDaDisciplina());
	}
	
	@Test
	void alterarNivelDaDisciplina2() {
		dis.setNivelDaDisciplina(NivelEscolar.FUNDAMENTAL);
		assertEquals("Fundamental", dis.getNivelDaDisciplina());
	}
	
	@Test
	void alterarNivelDaDisciplina3() {
		assertThrows(NullPointerException.class, () -> {
			dis.setNivelDaDisciplina(null);
		});
	}
	
}
