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
import app.modelo.infra.TurmaDAO;

public class AlunoDAOTeste {
	AlunoDAO dao;
	Aluno alun1;
	Aluno alun2;
	static Turma t1;
	static Turma t2;
	
	@BeforeAll
	static void inicilizarSecundarios() {
		TurmaDAO turmDAO = new TurmaDAO();
		t1 = turmDAO.criarTurma(new Turma(NivelEscolar.ENSINO_MEDIO, "Z", "MP65"));
		t2 = turmDAO.criarTurma(new Turma(NivelEscolar.FUNDAMENTAL, "Z", "MP65"));
	}
	
	@BeforeEach
	void inicializarDAOeAluno() {
		dao = new AlunoDAO();
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		alun2 = new Aluno("Josias", "95383664173", "Masculino", "josiasmalafaia@gmail.com", sqlDate, t1);
		alun1 = new Aluno("Josias", "30282548670", "Masculino", "josias@gmail.com", sqlDate, t1);
		dao.criarAluno(alun2);
		dao.criarAluno(alun1);
	}
	
	@AfterAll
	static void removerSecundarios() {
		TurmaDAO turmDAO = new TurmaDAO();
		turmDAO.removerTurma("EMZ");
		turmDAO.removerTurma("EFZ");
	}
	
	@AfterEach
	void removerAluno() {
		try {
			dao.removerAluno("30282548670");
			dao.removerAluno("95383664173");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	void testarCriacao1() {			
		assertTrue(alun1.equals(dao.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.criarAluno(alun1);				
		});
	}
	
	@Test
	void testarCriacao3() {
		assertThrows(NullPointerException.class, () -> {
			dao.criarAluno(null);				
		});
	}
	
	@Test
	void testarRemocao1() {
		Aluno a = dao.removerAluno("30282548670");
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
		boolean verificar = a.equals(alun1);
		dao.criarAluno(new Aluno("Josias", "30282548670", "Masculino", "josias@gmail.com", sqlDate, t1));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao2() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.removerAluno("99999999999");
		});
	}
	
	@Test
	void testarRemocao3() {
		assertThrows(NullPointerException.class, () -> {
			dao.removerAluno(null);
		});
	}
	
	@Test
	void alteracaoNula1() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar(null, AtributosAluno.NOME, "josevaldo ferreira");
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
			dao.Atualizar("30282548670", AtributosAluno.NOME, null);
		});
	}
	
	@Test
	void alteracaoCPFInvalido() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.Atualizar("99999999999", AtributosAluno.NOME, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNome1() {
		dao.Atualizar("30282548670", AtributosAluno.NOME, "josevaldo ferreira");
		assertTrue("Josevaldo Ferreira".equals(dao.obterUltimo().getNome()));
	}
	
	@Test
	void alteracaoCPF1() {
		dao.Atualizar("30282548670", AtributosAluno.CPF, "23901549218");
		boolean verificar = "23901549218".equals(dao.obterUltimo().getCPF());
		dao.Atualizar("23901549218", AtributosAluno.CPF, "30282548670");
		assertTrue(verificar);
	}
	
	@Test
	void alteracaoCPF2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.Atualizar("30282548670", AtributosAluno.CPF, "95383664173");
		});
	}
	
	@Test
	void alteracaoCPF3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("30282548670", AtributosAluno.CPF, "asdasdasd");
		});
	}
	
	@Test
	void alteracaoSexo() {
		dao.Atualizar("30282548670", AtributosAluno.SEXO, "feminino");
		assertTrue("Feminino".equals(dao.obterUltimo().getSexo()));
	}
	
	@Test
	void alteracaoEmail1() {
		dao.Atualizar("30282548670", AtributosAluno.EMAIL_DO_RESPONSAVEL, "josiasmalista@gmail.com");
		assertTrue("josiasmalista@gmail.com".equals(dao.obterUltimo().getEmail()));
	}
	
	@Test
	void alteracaoEmail2() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("30282548670", AtributosAluno.EMAIL_DO_RESPONSAVEL, "dsdsd");
		});
	}
	
	@Test
	void alteracaoDataNascimento1() {
		dao.Atualizar("30282548670", AtributosAluno.DATA_NASCIMENTO, "12-10-2002");
		assertTrue("2002-10-12".equals(dao.obterUltimo().getDataNascimento().toString()));
	}
	
	@Test
	void alteracaoDataNascimento2() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("30282548670", AtributosAluno.DATA_NASCIMENTO, "xa-10-2002");			
		});
	}
	
	@Test
	void alteracaoDataNascimento3() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("30282548670", AtributosAluno.DATA_NASCIMENTO, "asd");			
		});
	}
	
	@Test
	void alteracaoTurma1() {
		dao.Atualizar("30282548670", AtributosAluno.TURMA, "EFZ");
		assertTrue(dao.getAlunoPorCPF("30282548670").getTurma().equals(t2));
	}
	
	@Test
	void alteracaoTurma2() {
		dao.Atualizar("30282548670", AtributosAluno.TURMA, null);
		assertTrue(dao.getAlunoPorCPF("30282548670").getTurma() == null);
	}
	
	@Test
	void mudancaMatricula1() {
		dao.Atualizar("30282548670", AtributosAluno.CPF, "23901549218");
		boolean verificar = "92183520230228".equals(dao.obterUltimo().getMatricula());
		dao.Atualizar("23901549218", AtributosAluno.CPF, "30282548670");
		assertTrue(verificar);
	}
	
	@Test
	void mudancaMatricula2() {
		dao.Atualizar("30282548670", AtributosAluno.DATA_NASCIMENTO, "12-10-2002");
		boolean verificar = "86703820021012".equals(dao.obterUltimo().getMatricula());
		assertTrue(verificar);
	}
	
}
