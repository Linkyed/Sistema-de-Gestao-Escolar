package app.model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.AreasDeConhecimento;
import app.modelo.Funcionalidades;
import app.modelo.Professor;
import app.modelo.infra.DAO;
import app.modelo.infra.ProfessorDAO;

public class ProfessorDAOTeste {

	ProfessorDAO dao;
	Professor prof;
	
	@BeforeEach
	void inicializarDAOeProfessor() {
		dao = new ProfessorDAO();
		Date sqlDate = Funcionalidades.cirarDataSQL(10, 3, 2023);
		prof = new Professor("Josias", "30282548670", "Masculino", "josias@gmail.com", AreasDeConhecimento.GEOGRAFIA, 5050.50, sqlDate);
	}
	
	@AfterEach
	void removerProfessor() {
		try {
			dao.removerProfessor("30282548670");			
		} catch (Exception e) {

		}
	}
	
	@Test
	void testarCriacao1() {
		dao.criarProfessor(prof);			
		assertTrue(prof.equals(dao.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		dao.criarProfessor(prof);
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.criarProfessor(prof);				
		});
	}
	
	@Test
	void testarCriacao3() {
		assertThrows(NullPointerException.class, () -> {
			dao.criarProfessor(null);				
		});
	}
	
	@Test
	void testarRemocao1() {
		dao.criarProfessor(prof);
		Professor p = dao.removerProfessor("30282548670");
		assertTrue(p.equals(prof));
	}
	
	@Test
	void testarRemocao2() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.removerProfessor("99999999999");
		});
	}
	
	@Test
	void testarRemocao3() {
		assertThrows(NullPointerException.class, () -> {
			dao.removerProfessor(null);
		});
	}
	
}
