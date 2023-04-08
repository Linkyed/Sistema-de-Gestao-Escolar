package app.modelo;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.excecao.ConsultaNulaException;
import app.excecao.RegistroDuplicadoException;
import app.modelo.infra.DAOs;


public class DisciplinaDAOTeste {
	Disciplina disc1;
	Disciplina disc2;
	Disciplina disc3;
	
	@BeforeEach
	void inicializarDAOeDisciplina() {
		disc2 = new Disciplina("artes", 120, "fundamental", "ART");
		disc3 = new Disciplina("artes", 120, "ensino medio", "ART");
		disc1 = new Disciplina("geografia", 120, "ensino medio", "GEO");
		DAOs.discDAO.criarDisciplina(disc2);
		DAOs.discDAO.criarDisciplina(disc3);
		DAOs.discDAO.criarDisciplina(disc1);
	}
	
	@AfterEach
	void removerDisciplina() {
		DAOs.discDAO.removerDisciplina("ART01");
		DAOs.discDAO.removerDisciplina("ART02");
		DAOs.discDAO.removerDisciplina("GEO02");
	}
	
	@Test
	void testarCriacao1() {			
		assertTrue(disc1.equals(DAOs.discDAO.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.discDAO.criarDisciplina(disc1);				
		});
	}
	
	@Test
	void testarCriacao3() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.discDAO.criarDisciplina(null);				
		});
	}
	
	@Test
	void testarRemocao1() {
		Disciplina d = DAOs.discDAO.removerDisciplina("GEO02");
		boolean verificar = d.equals(disc1);
		DAOs.discDAO.criarDisciplina(new Disciplina("GEOGRAFIA", 120, "ENSINO MEDIO", "GEO"));
		assertTrue(verificar);
	}
	
	
	@Test
	void testarRemocao2() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.discDAO.removerDisciplina("DDDDD");
		});
	}
	
	@Test
	void testarRemocao3() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.discDAO.removerDisciplina(null);
		});
	}
	
	@Test
	void alteracaoNula1() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.discDAO.Atualizar(null, AtributosDisciplina.NOME, "filosofia");
		});
	}
	
	@Test
	void alteracaoNula2() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.discDAO.Atualizar("ART01", null, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNula3() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.discDAO.Atualizar("ART01", AtributosDisciplina.NOME, null);
		});
	}
	
	@Test
	void alteracaoVazia() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.discDAO.Atualizar("ART01", AtributosDisciplina.NOME, "");
		});
	}
	
	@Test
	void alteracaoCodigoInvalido() {
		assertThrows(NullPointerException.class, () -> {
			DAOs.discDAO.Atualizar("FFFFF", AtributosDisciplina.NOME, "geografia");
		});
	}
	
	@Test
	void alteracaoNome() {
		DAOs.discDAO.Atualizar("GEO02", AtributosDisciplina.NOME, "filosofia");
		boolean verificacao = "Filosofia".equals(DAOs.discDAO.obterUltimo().getNome());
		assertTrue(verificacao);
	}
	
	@Test
	void alteracaoCodigo() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.discDAO.Atualizar("GEO02", AtributosDisciplina.CODIGO, "ART");
			
		});
	}
	
	@Test
	void alteracaoCodigoComNivelDisciplina() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.discDAO.Atualizar("ART02", AtributosDisciplina.NIVEL_DISCIPLINA, "fundamental");
			
		});
	}
	
	@Test
	void alterarCargaHoraria1() {
		DAOs.discDAO.Atualizar("GEO02", AtributosDisciplina.CARGA_HORARIA, "200");
		assertEquals(200, DAOs.discDAO.obterUltimo().getCargaHoraria());
	}
	
	@Test
	void alterarCargaHoraria2() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.discDAO.Atualizar("GEO02", AtributosDisciplina.CARGA_HORARIA, "asd");			
		});
	}
	
	@Test
	void alterarNivelDisciplina1() {
		DAOs.discDAO.Atualizar("GEO02", AtributosDisciplina.NIVEL_DISCIPLINA, "fundamental");
		boolean verificacao = "Fundamental".equalsIgnoreCase(DAOs.discDAO.obterUltimo().getNivelDaDisciplina());
		DAOs.discDAO.Atualizar("GEO01", AtributosDisciplina.NIVEL_DISCIPLINA, "ensino medio");
		assertTrue(verificacao);
	}
	
	@Test
	void alterarNivelDisciplina2() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.discDAO.Atualizar("ART01", AtributosDisciplina.NIVEL_DISCIPLINA, "nada");			
		});
	}
}
