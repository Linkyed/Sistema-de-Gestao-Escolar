package app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.modelo.AreasDeConhecimento;
import app.modelo.Funcionalidades;
import app.modelo.Professor;

public class ProfessorTeste {

	Professor prof;
	
	@BeforeEach
	void iniciarProfessor() throws ParseException {
		Date sqlDate = Funcionalidades.cirarDataSQL("10-03-2023");
		prof = new Professor("Josias", "93774484090", "Masculino", "josias@gmail.com", AreasDeConhecimento.GEOGRAFIA, 5050.50, sqlDate);
	}
	
	@Test
	void alterarNome1() {
		prof.setNome("matias bInotO FERRARI");
		assertEquals("Matias Binoto Ferrari", prof.getNome());
	}
	
	@Test
	void alterarNome2() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setNome(" ");
		});
	}
	
	@Test
	void alterarNome3() {
		assertThrows(NullPointerException.class, () -> {
			prof.setNome(null);
		});
	}
	
	@Test
	void alterarSexo1() {
		prof.setSexo("  maSCuliNo    ");
		assertEquals("Masculino", prof.getSexo());
	}
	
	@Test
	void alterarSexo2() {
		prof.setSexo(" Feminino ");
		assertEquals("Feminino", prof.getSexo());
	}
	
	@Test
	void alterarSexo3() {
		prof.setSexo("OUTRO");
		assertEquals("Outro", prof.getSexo());
	}
	
	@Test
	void alterarSexo4() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setSexo("XXXXX");
		});
	}
	
	@Test
	void alterarEmail1() {
		prof.setEmail("josiasbonifaio@gmail.com");
		assertEquals("josiasbonifaio@gmail.com", prof.getEmail());
	}
	
	@Test
	void alterarEmail2() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setEmail("josiasbonifaio@gmail");
		});
	}
	
	@Test
	void alterarEmail3() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setEmail("josiasbonifaiogmail.com");
		});
	}
	
	@Test
	void alterarSalario1() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setSalario(-100.0);
		});
	}
	
	@Test
	void alterarSalario2() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setSalario(1000000000.0);
		});
	}
	
	@Test
	void alterarSalario3() {
		prof.setSalario(11520.20);
		assertEquals(11520.20, prof.getSalario());
	}
	
	@Test
	void alterarContrato1() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL("29-02-2023");
			prof.setInicioContrato(sqlDate);
		});
	}
	
	@Test
	void alterarContrato2() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL("32-03-2023");
			prof.setInicioContrato(sqlDate);
		});
	}
	
	@Test
	void alterarContrato3() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL("31-04-2023");
			prof.setInicioContrato(sqlDate);
		});
	}
	
	@Test
	void alterarContrato4() {
		Date sqlDate = Funcionalidades.cirarDataSQL("31-03-2023");
		prof.setInicioContrato(sqlDate);
		assertEquals(prof.getInicioContrato(), sqlDate);
	}
	
	@Test
	void alterarCPF1() {
		prof.setCPF("08023420518");
		assertEquals(prof.getCPF(), "08023420518");
	}
	
	@Test
	void alterarCPF2() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setCPF("000123456789");
		});
	}
	
	@Test
	void alterarCPF3() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setCPF("10");
		});
	}
	
	@Test
	void alterarAreaDeFormacao1() {
		prof.setAreaDeFormacao(AreasDeConhecimento.GEOGRAFIA);
		assertEquals("Geografia", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao2() {
		prof.setAreaDeFormacao(AreasDeConhecimento.ARTES);
		assertEquals("Artes", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao3() {
		prof.setAreaDeFormacao(AreasDeConhecimento.BIOLOGIA);
		assertEquals("Biologia", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao4() {
		prof.setAreaDeFormacao(AreasDeConhecimento.EDUCACAO_FISICA);
		assertEquals("Educação Física", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao5() {
		prof.setAreaDeFormacao(AreasDeConhecimento.FILOSOFIA);
		assertEquals("Filosofia", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao6() {
		prof.setAreaDeFormacao(AreasDeConhecimento.HISTORIA);
		assertEquals("História", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao7() {
		prof.setAreaDeFormacao(AreasDeConhecimento.MATEMATICA);
		assertEquals("Matemática", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao8() {
		prof.setAreaDeFormacao(AreasDeConhecimento.FISICA);
		assertEquals("Física", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao9() {
		prof.setAreaDeFormacao(AreasDeConhecimento.QUIMICA);
		assertEquals("Química", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao10() {
		prof.setAreaDeFormacao(AreasDeConhecimento.SOCIOLOGIA);
		assertEquals("Sociologia", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao11() {
		prof.setAreaDeFormacao(AreasDeConhecimento.LINGUA_PORTUGUESA);
		assertEquals("Língua Portuguesa", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao12() {
		prof.setAreaDeFormacao(AreasDeConhecimento.LITERATURA);
		assertEquals("Literatura", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao13() {
		prof.setAreaDeFormacao(AreasDeConhecimento.LINGUA_INGLESA);
		assertEquals("Língua Inglesa", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao14() {
		prof.setAreaDeFormacao(AreasDeConhecimento.LINGUA_ALEMA);
		assertEquals("Língua Alemã", prof.getAreaDeFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao15() {
		prof.setAreaDeFormacao(AreasDeConhecimento.LINGUA_FRANCESA);
		assertEquals("Língua Francesa", prof.getAreaDeFormacao());
	}
	
	
	@Test
	void alterarAreaDeFormacao16() {
		assertThrows(NullPointerException.class, () -> {
			prof.setAreaDeFormacao(null);
		});
	}
	
}
