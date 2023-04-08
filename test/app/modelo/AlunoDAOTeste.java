package app.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.infra.AlunoDAO;
import app.modelo.infra.DAOs;
import app.modelo.infra.TurmaDAO;

public class AlunoDAOTeste {
	Aluno alun1;
	Aluno alun2;

	@BeforeEach
	void inicializarDAOeAluno() {
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		alun2 = new Aluno("Josias", "95383664173", "Masculino", "josiasmalafaia@gmail.com", sqlDate);
		alun1 = new Aluno("Josias", "30282548670", "Masculino", "josias@gmail.com", sqlDate);
		DAOs.alunDAO.criarAluno(alun2);
		DAOs.alunDAO.criarAluno(alun1);
	}
	
	@AfterEach
	void removerAluno() {
		DAOs.alunDAO.removerAluno("30282548670");
		DAOs.alunDAO.removerAluno("95383664173");
	}
	
	@Test
	void testarCriacao1() {			
		assertTrue(alun1.equals(DAOs.alunDAO.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.alunDAO.criarAluno(alun1);				
		});
	}
	
	@Test
	void testarCriacao3() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.alunDAO.criarAluno(null);				
		});
	}
	
	@Test
	void testarRemocao1() {
		Aluno a = DAOs.alunDAO.removerAluno("30282548670");
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		boolean verificar = a.equals(alun1);
		DAOs.alunDAO.criarAluno(new Aluno("Josias", "30282548670", "Masculino", "josias@gmail.com", sqlDate));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao2() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.alunDAO.removerAluno("99999999999");
		});
	}
	
	@Test
	void testarRemocao3() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.alunDAO.removerAluno(null);
		});
	}
	
	@Test
	void alteracaoNula1() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.alunDAO.Atualizar(null, AtributosAluno.NOME, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNula2() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.alunDAO.Atualizar("30282548670", null, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNula3() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.NOME, null);
		});
	}
	
	@Test
	void alteracaoCPFInvalido() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.alunDAO.Atualizar("99999999999", AtributosAluno.NOME, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNome1() {
		DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.NOME, "josevaldo ferreira");
		assertTrue("Josevaldo Ferreira".equals(DAOs.alunDAO.obterUltimo().getNome()));
	}
	
	@Test
	void alteracaoCPF1() {
		DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.CPF, "23901549218");
		boolean verificar = "23901549218".equals(DAOs.alunDAO.obterUltimo().getCPF());
		DAOs.alunDAO.Atualizar("23901549218", AtributosAluno.CPF, "30282548670");
		assertTrue(verificar);
	}
	
	@Test
	void alteracaoCPF2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.CPF, "95383664173");
		});
	}
	
	@Test
	void alteracaoCPF3() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.CPF, "asdasdasd");
		});
	}
	
	@Test
	void alteracaoSexo() {
		DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.SEXO, "feminino");
		assertTrue("Feminino".equals(DAOs.alunDAO.obterUltimo().getSexo()));
	}
	
	@Test
	void alteracaoEmail1() {
		DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.EMAIL_DO_RESPONSAVEL, "josiasmalista@gmail.com");
		assertTrue("josiasmalista@gmail.com".equals(DAOs.alunDAO.obterUltimo().getEmail()));
	}
	
	@Test
	void alteracaoEmail2() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.EMAIL_DO_RESPONSAVEL, "dsdsd");
		});
	}
	
	@Test
	void alteracaoDataNascimento1() {
		DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.DATA_NASCIMENTO, "12-10-2002");
		assertTrue("2002-10-12".equals(DAOs.alunDAO.obterUltimo().getDataNascimento().toString()));
	}
	
	@Test
	void alteracaoDataNascimento2() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.DATA_NASCIMENTO, "xa-10-2002");			
		});
	}
	
	@Test
	void alteracaoDataNascimento3() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.alunDAO.Atualizar("30282548670", AtributosAluno.DATA_NASCIMENTO, "asd");			
		});
	}
	
}
