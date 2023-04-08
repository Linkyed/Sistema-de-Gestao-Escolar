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
import app.modelo.infra.DAOs;
import app.modelo.infra.DisciplinaDAO;
import app.modelo.infra.ProfessorDAO;
import app.modelo.infra.TurmaDAO;

public class ProfessorDAOTeste {

	Professor prof1;
	Professor prof2;

	
	@BeforeEach
	void inicializarDAOeProfessor() {
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		prof2 = new Professor("Josias", "95383664173", "Masculino", "josiasmalafaia@gmail.com", "Geografia", 5050.50, sqlDate);
		prof1 = new Professor("Josias", "30282548670", "Masculino", "josias@gmail.com", "Geografia", 5050.50, sqlDate);
		DAOs.profDAO.criarProfessor(prof2);
		DAOs.profDAO.criarProfessor(prof1);
	}
	
	@AfterEach
	void removerProfessor() {
		try {
			DAOs.profDAO.removerProfessor("30282548670");
			DAOs.profDAO.removerProfessor("95383664173");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	void testarCriacao1() {			
		assertTrue(prof1.equals(DAOs.profDAO.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.profDAO.criarProfessor(prof1);				
		});
	}
	
	@Test
	void testarCriacao3() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.profDAO.criarProfessor(null);				
		});
	}
	
	@Test
	void testarRemocao1() {
		Professor p = DAOs.profDAO.removerProfessor("30282548670");
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		boolean verificar = p.equals(prof1);
		DAOs.profDAO.criarProfessor(new Professor("Josias", "30282548670", "Masculino", "josias@gmail.com", "geografia", 5050.50, sqlDate));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao2() {
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.DISCIPLINAS_ADICIONAR, "ART01");
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.TURMAS_ADICIONAR, "EMZ");
		Professor p = DAOs.profDAO.removerProfessor("30282548670");
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		boolean verificar = p.equals(prof1);
		DAOs.profDAO.criarProfessor(new Professor("Josias", "30282548670", "Masculino", "josias@gmail.com", "geografia", 5050.50, sqlDate));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao3() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.profDAO.removerProfessor("99999999999");
		});
	}
	
	@Test
	void testarRemocao4() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.profDAO.removerProfessor(null);
		});
	}
	
	@Test
	void alteracaoNula1() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.profDAO.Atualizar(null, AtributosProfessor.NOME, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNula2() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", null, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNula3() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.NOME, null);
		});
	}
	
	@Test
	void alteracaoCPFInvalido() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.profDAO.Atualizar("99999999999", AtributosProfessor.NOME, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNome1() {
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.NOME, "josevaldo ferreira");
		assertTrue("Josevaldo Ferreira".equals(DAOs.profDAO.obterUltimo().getNome()));
	}
	
	@Test
	void alteracaoCPF1() {
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.CPF, "23901549218");
		boolean verificar = "23901549218".equals(DAOs.profDAO.obterUltimo().getCPF());
		DAOs.profDAO.Atualizar("23901549218", AtributosProfessor.CPF, "30282548670");
		assertTrue(verificar);
	}
	
	@Test
	void alteracaoCPF2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.CPF, "95383664173");
		});
	}
	
	@Test
	void alteracaoCPF3() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.CPF, "asdasdasd");
		});
	}
	
	@Test
	void alteracaoSexo() {
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.SEXO, "feminino");
		assertTrue("Feminino".equals(DAOs.profDAO.obterUltimo().getSexo()));
	}
	
	@Test
	void alteracaoEmail1() {
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.EMAIL, "josiasmalista@gmail.com");
		assertTrue("josiasmalista@gmail.com".equals(DAOs.profDAO.obterUltimo().getEmail()));
	}

	@Test
	void alteracaoEmail2() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.EMAIL, "dsdsd");
		});
	}
	
	@Test
	void alteracaoFormacao1() {
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.FORMACAO, "artes");
		assertTrue("Artes".equals(DAOs.profDAO.obterUltimo().getFormacao()));
	}
	
	@Test
	void alteracaoFormacao2() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.FORMACAO, "");
		});
	}
	
	@Test
	void alteracaoSalario1() {
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.SALARIO, "12000.40");
		assertEquals(12000.40, DAOs.profDAO.obterUltimo().getSalario());
	}
	
	@Test
	void alteracaoSalario2() {
		assertThrows(NumberFormatException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.SALARIO, "asd");			
		});
	}
	
	@Test
	void alteracaoInicioContrato1() {
		DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.INICIO_CONTRATO, "12-10-2002");
		assertTrue("2002-10-12".equals(DAOs.profDAO.obterUltimo().getInicioContrato().toString()));
	}
	
	@Test
	void alteracaoInicioContrato2() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.INICIO_CONTRATO, "xa-10-2002");			
		});
	}
	
	@Test
	void alteracaoInicioContrato3() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.profDAO.Atualizar("30282548670", AtributosProfessor.INICIO_CONTRATO, "asd");			
		});
	}
	
}
