package app.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.ParseException;
import java.sql.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProfessorTeste {

	Professor prof;
	static Disciplina d;
	static Turma t;
	
	@BeforeEach
	void iniciarProfessor() throws ParseException {
		Date sqlDate = Funcionalidades.cirarDataSQL("10-03-2023");
		prof = new Professor("Josias", "93774484090", "Masculino", "josias@gmail.com", "geografia", 5050.50, sqlDate);
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
	void alterarSexo5() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setSexo("");
		});
	}
	
	@Test
	void alterarSexo6() {
		assertThrows(NullPointerException.class, () -> {
			prof.setSexo(null);
		});
	}
	
	@Test
	void alterarEmail1() {
		prof.setEmail("   josiasbonifaio@gmail.com    ");
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
	void alterarEmail4() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setEmail("");
		});
	}
	
	@Test
	void alterarEmail5() {
		assertThrows(NullPointerException.class, () -> {
			prof.setEmail(null);
		});
	}
	
	@Test
	void alterarSalario1() {
		prof.setSalario(11520.20);
		assertEquals(11520.20, prof.getSalario());
	}
	
	@Test
	void alterarSalario2() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setSalario(-100.0);
		});
	}
	
	@Test
	void alterarSalario3() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setSalario(1000000000.0);
		});
	}
	
	@Test
	void alterarSalario4() {
		assertThrows(NullPointerException.class, () -> {
			prof.setSalario(null);
		});
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
		assertThrows(IllegalArgumentException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL("");
			prof.setInicioContrato(sqlDate);
		});
	}
	
	@Test
	void alterarContrato5() {
		assertThrows(NullPointerException.class, () -> {
			Date sqlDate = Funcionalidades.cirarDataSQL(null);
			prof.setInicioContrato(sqlDate);
		});
	}
	
	@Test
	void alterarContrato6() {
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
	void alterarCPF4() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setCPF("");
		});
	}
	
	@Test
	void alterarCPF5() {
		assertThrows(NullPointerException.class, () -> {
			prof.setCPF(null);
		});
	}
	
	@Test
	void alterarAreaDeFormacao1() {
		prof.setFormacao("letras");
		assertEquals("Letras", prof.getFormacao());
	}
	
	@Test
	void alterarAreaDeFormacao2() {
		assertThrows(IllegalArgumentException.class, () -> {
			prof.setFormacao("");			
		});
	}
	
	@Test
	void alterarAreaDeFormacao3() {
		assertThrows(NullPointerException.class, () -> {
			prof.setFormacao(null);			
		});
	}
	
	
	
}
