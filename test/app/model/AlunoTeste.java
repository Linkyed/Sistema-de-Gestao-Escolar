package app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.modelo.Aluno;
import app.modelo.Funcionalidades;

public class AlunoTeste {
	Aluno alun;
	
	@BeforeEach
	void iniciarAluno(){
		Date sqlDate = Funcionalidades.cirarDataSQL(10, 3, 2023);
		alun = new Aluno("Thales ViTOr Costa", "05959533014", "Masculino", "thales@gmail.com", sqlDate);
	}
	
	@Test
	void alterarNome1() {
		alun.setNome("matias bInotO FERRARI");
		assertEquals("Matias Binoto Ferrari", alun.getNome());
	}
	
	@Test
	void alterarNome2() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setNome(" ");
		});
	}
	
	@Test
	void alterarNome3() {
		assertThrows(NullPointerException.class, () -> {
			alun.setNome(null);
		});
	}
	
	@Test
	void alterarSexo1() {
		alun.setSexo("  maSCuliNo    ");
		assertEquals("Masculino", alun.getSexo());
	}
	
	@Test
	void alterarSexo2() {
		alun.setSexo(" Feminino ");
		assertEquals("Feminino", alun.getSexo());
	}
	
	@Test
	void alterarSexo3() {
		alun.setSexo("OUTRO");
		assertEquals("Outro", alun.getSexo());
	}
	
	@Test
	void alterarSexo4() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setSexo("XXXXX");
		});
	}
	
	@Test
	void alterarEmail1() {
		alun.setEmail("josiasbonifaio@gmail.com");
		assertEquals("josiasbonifaio@gmail.com", alun.getEmail());
	}
	
	@Test
	void alterarEmail2() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setEmail("josiasbonifaio@gmail");
		});
	}
	
	@Test
	void alterarEmail3() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setEmail("josiasbonifaiogmail.com");
		});
	}
	
	@Test
	void alterarCPF1() {
		alun.setCPF("08023420518");
		assertEquals(alun.getCPF(), "08023420518");
	}
	
	@Test
	void alterarCPF2() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setCPF("000123456789");
		});
	}
	
	@Test
	void alterarCPF3() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setCPF("10");
		});
	}
	
	@Test
	void alterarContrato1() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL(29, 2, 2023);
			alun.setDataNacimento(sqlDate);
		});
	}
	
	@Test
	void alterarContrato2() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL(32, 3, 2023);
			alun.setDataNacimento(sqlDate);
		});
	}
	
	@Test
	void alterarContrato3() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL(31, 4, 2023);
			alun.setDataNacimento(sqlDate);
		});
	}
	
	@Test
	void alterarContrato4() {
		Date sqlDate = Funcionalidades.cirarDataSQL(31, 3, 2023);
		alun.setDataNacimento(sqlDate);
		assertEquals(alun.getDataNacimento(), sqlDate);
	}
	
	@Test
	void testarMatricula() {
		assertEquals("143920230310", alun.getMatricula());
	}
	
	
}
