package app.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AlunoTeste {
	Aluno alun;
	
	@BeforeEach
	void iniciarAluno(){
		Date sqlDate = Funcionalidades.cirarDataSQL("10-03-2023");
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
	void alterarSexo5() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setSexo("");
		});
	}
	

	@Test
	void alterarSexo6() {
		assertThrows(NullPointerException.class, () -> {
			alun.setSexo(null);
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
	void alterarEmail4() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setEmail(" ");
		});
	}
	
	@Test
	void alterarEmail5() {
		assertThrows(NullPointerException.class, () -> {
			alun.setEmail(null);
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
	void alterarCPF4() {
		assertThrows(IllegalArgumentException.class, () -> {
			alun.setCPF("");
		});
	}
	
	@Test
	void alterarCPF5() {
		assertThrows(NullPointerException.class, () -> {
			alun.setCPF(null);
		});
	}
	
	@Test
	void alterarDataNascimento1() {
		Date sqlDate = Funcionalidades.cirarDataSQL("31-03-2023");
		alun.setDataNascimento(sqlDate);
		assertEquals(alun.getDataNascimento(), sqlDate);
	}
	
	@Test
	void alterarDataNascimento2() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL("29-02-2023");
			alun.setDataNascimento(sqlDate);
		});
	}
	
	@Test
	void alterarDataNascimento3() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL("32-03-2023");
			alun.setDataNascimento(sqlDate);
		});
	}
	
	@Test
	void alterarDataNascimento4() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL("31-04-2023");
			alun.setDataNascimento(sqlDate);
		});
	}
	
	@Test
	void alterarDataNascimento5() {
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL("");
			alun.setDataNascimento(sqlDate);
		});
	}
	
	@Test
	void alterarDataNascimento6() {
		assertThrows(NullPointerException.class, () -> {
			alun.setDataNascimento(null);
		});
	}
	
	
}
