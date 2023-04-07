package app.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.infra.DisciplinaDAO;
import app.modelo.infra.ProfessorDAO;
import app.modelo.infra.TurmaDAO;

public class ProfessorDAOTeste {

	ProfessorDAO dao;
	Professor prof1;
	Professor prof2;
	
	@BeforeAll
	static void iniciarSecundarios() {
		DisciplinaDAO dDAO = new DisciplinaDAO();
		TurmaDAO tDAO = new TurmaDAO();
		dDAO.criarDisciplina(new Disciplina(AreasDeConhecimento.ARTES, 120, NivelEscolar.FUNDAMENTAL));
		tDAO.criarTurma(new Turma(NivelEscolar.ENSINO_MEDIO, "Z", "MP65"));
	}
	
	@BeforeEach
	void inicializarDAOeProfessor() {
		dao = new ProfessorDAO();
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		prof2 = new Professor("Josias", "95383664173", "Masculino", "josiasmalafaia@gmail.com", AreasDeConhecimento.GEOGRAFIA, 5050.50, sqlDate);
		prof1 = new Professor("Josias", "30282548670", "Masculino", "josias@gmail.com", AreasDeConhecimento.GEOGRAFIA, 5050.50, sqlDate);
		dao.criarProfessor(prof2);
		dao.criarProfessor(prof1);
	}
	
	@AfterAll
	static void removerSecundarios() {
		DisciplinaDAO dDAO = new DisciplinaDAO();
		TurmaDAO tDAO = new TurmaDAO();
		dDAO.removerDisciplina("ART01");
		tDAO.removerTurma("EMZ");
		dDAO.fechar();
		tDAO.fechar();
	}
	
	@AfterEach
	void removerProfessor() {
		try {
			dao.removerProfessor("30282548670");
			dao.removerProfessor("95383664173");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	void testarCriacao1() {			
		assertTrue(prof1.equals(dao.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.criarProfessor(prof1);				
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
		Professor p = dao.removerProfessor("30282548670");
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		boolean verificar = p.equals(prof1);
		dao.criarProfessor(new Professor("Josias", "30282548670", "Masculino", "josias@gmail.com", AreasDeConhecimento.GEOGRAFIA, 5050.50, sqlDate));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao2() {
		dao.Atualizar("30282548670", AtributosProfessor.DISCIPLINAS_ADICIONAR, "ART01");
		dao.Atualizar("30282548670", AtributosProfessor.TURMAS_ADICIONAR, "EMZ");
		Professor p = dao.removerProfessor("30282548670");
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		boolean verificar = p.equals(prof1);
		dao.criarProfessor(new Professor("Josias", "30282548670", "Masculino", "josias@gmail.com", AreasDeConhecimento.GEOGRAFIA, 5050.50, sqlDate));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao3() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.removerProfessor("99999999999");
		});
	}
	
	@Test
	void testarRemocao4() {
		assertThrows(NullPointerException.class, () -> {
			dao.removerProfessor(null);
		});
	}
	
	@Test
	void alteracaoNula1() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar(null, AtributosProfessor.NOME, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNula2() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar("30282548670", null, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNula3() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.NOME, null);
		});
	}
	
	@Test
	void alteracaoCPFInvalido() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.Atualizar("99999999999", AtributosProfessor.NOME, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNome1() {
		dao.Atualizar("30282548670", AtributosProfessor.NOME, "josevaldo ferreira");
		assertTrue("Josevaldo Ferreira".equals(dao.obterUltimo().getNome()));
	}
	
	@Test
	void alteracaoCPF1() {
		dao.Atualizar("30282548670", AtributosProfessor.CPF, "23901549218");
		boolean verificar = "23901549218".equals(dao.obterUltimo().getCPF());
		dao.Atualizar("23901549218", AtributosProfessor.CPF, "30282548670");
		assertTrue(verificar);
	}
	
	@Test
	void alteracaoCPF2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.CPF, "95383664173");
		});
	}
	
	@Test
	void alteracaoCPF3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.CPF, "asdasdasd");
		});
	}
	
	@Test
	void alteracaoSexo() {
		dao.Atualizar("30282548670", AtributosProfessor.SEXO, "feminino");
		assertTrue("Feminino".equals(dao.obterUltimo().getSexo()));
	}
	
	@Test
	void alteracaoEmail1() {
		dao.Atualizar("30282548670", AtributosProfessor.EMAIL, "josiasmalista@gmail.com");
		assertTrue("josiasmalista@gmail.com".equals(dao.obterUltimo().getEmail()));
	}
	
	@Test
	void alteracaoEmail2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.EMAIL, "josiasmalafaia@gmail.com");
		});
	}

	@Test
	void alteracaoEmail3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.EMAIL, "dsdsd");
		});
	}
	
	@Test
	void alteracaoFormacao1() {
		dao.Atualizar("30282548670", AtributosProfessor.FORMACAO, "artes");
		assertTrue("Artes".equals(dao.obterUltimo().getAreaDeFormacao()));
	}
	
	@Test
	void alteracaoFormacao2() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.FORMACAO, "XDD");
		});
	}
	
	@Test
	void alteracaoSalario1() {
		dao.Atualizar("30282548670", AtributosProfessor.SALARIO, "12000.40");
		assertEquals(12000.40, dao.obterUltimo().getSalario());
	}
	
	@Test
	void alteracaoSalario2() {
		assertThrows(NumberFormatException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.SALARIO, "asd");			
		});
	}
	
	@Test
	void alteracaoInicioContrato1() {
		dao.Atualizar("30282548670", AtributosProfessor.INICIO_CONTRATO, "12-10-2002");
		assertTrue("2002-10-12".equals(dao.obterUltimo().getInicioContrato().toString()));
	}
	
	@Test
	void alteracaoInicioContrato2() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.INICIO_CONTRATO, "xa-10-2002");			
		});
	}
	
	@Test
	void alteracaoInicioContrato3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("30282548670", AtributosProfessor.INICIO_CONTRATO, "asd");			
		});
	}
	
	@Test
	void alteracaoDisciplinas() {
		dao.Atualizar("30282548670", AtributosProfessor.DISCIPLINAS_ADICIONAR, "ART01");
		assertTrue(dao.getProfessorPorCPF("30282548670").getDisciplinas().get(0).getCodigo().equals("ART01"));
		dao.Atualizar("30282548670", AtributosProfessor.DISCIPLINAS_REMOVER, "ART01");
		assertEquals(dao.getProfessorPorCPF("30282548670").getDisciplinas().size(), 0);
	}
	
	@Test
	void alteracaoTurmas() {
		dao.Atualizar("30282548670", AtributosProfessor.TURMAS_ADICIONAR, "EMZ");
		assertTrue(dao.getProfessorPorCPF("30282548670").getTurmas().get(0).getCodigo().equals("EMZ"));
		dao.Atualizar("30282548670", AtributosProfessor.TURMAS_REMOVER, "EMZ");
		assertEquals(dao.getProfessorPorCPF("30282548670").getTurmas().size(), 0);
	}
	
}
