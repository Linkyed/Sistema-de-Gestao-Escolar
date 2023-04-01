package app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.modelo.Professor;

public class ProfessorTeste {

	Professor prof;
	
	@BeforeEach
	void iniciarProfessor() throws ParseException {
		DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date data = formato.parse("17/10/2002");
		Timestamp dataHora = new Timestamp(data.getTime());
		prof = new Professor("Josias", "Masculino", "josias@gmail.com", 5050.50, dataHora);
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
	
}
