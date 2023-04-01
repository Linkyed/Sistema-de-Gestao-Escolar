package app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.modelo.Professor;

public class ProfessorTeste {

	Professor prof;
	
	@BeforeEach
	void iniciarProfessor() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2023, Calendar.MONTH, 28);
		Date date = calendar.getTime();
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		prof = new Professor("Josias", "Masculino", "josias@gmail.com", 5050.50, sqlDate);
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
	
}
