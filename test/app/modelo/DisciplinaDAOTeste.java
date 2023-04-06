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
import app.modelo.Aluno;
import app.modelo.AreasDeConhecimento;
import app.modelo.AtributosAluno;
import app.modelo.AtributosDisciplina;
import app.modelo.Disciplina;
import app.modelo.Funcionalidades;
import app.modelo.NivelEscolar;
import app.modelo.infra.AlunoDAO;
import app.modelo.infra.DisciplinaDAO;

public class DisciplinaDAOTeste {
	DisciplinaDAO dao;
	Disciplina disc1;
	Disciplina disc2;
	Disciplina disc3;
	
	@BeforeEach
	void inicializarDAOeAluno() {
		dao = new DisciplinaDAO();
		disc3 = new Disciplina(AreasDeConhecimento.ARTES, 120, NivelEscolar.FUNDAMENTAL);
		disc2 = new Disciplina(AreasDeConhecimento.ARTES, 120, NivelEscolar.ENSINO_MEDIO);
		disc1 = new Disciplina(AreasDeConhecimento.GEOGRAFIA, 120, NivelEscolar.ENSINO_MEDIO);
		dao.criarDisciplina(disc3);
		dao.criarDisciplina(disc2);
		dao.criarDisciplina(disc1);
	}
	
	@AfterEach
	void removerAluno() {
		try {
			dao.removerDisciplina("ART01");
			dao.removerDisciplina("ART02");
			dao.removerDisciplina("GEO02");
		} catch (Exception e) {
			
		}
	}
	
	@Test
	void testarCriacao1() {			
		assertTrue(disc1.equals(dao.obterUltimo()));		
	}
	
	@Test
	void testarCriacao2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.criarDisciplina(disc1);				
		});
	}
	
	@Test
	void testarCriacao3() {
		assertThrows(NullPointerException.class, () -> {
			dao.criarDisciplina(null);				
		});
	}
	
	@Test
	void testarRemocao1() {
		Disciplina d = dao.removerDisciplina("GEO02");
		boolean verificar = d.equals(disc1);
		dao.criarDisciplina(new Disciplina(AreasDeConhecimento.GEOGRAFIA, 120, NivelEscolar.ENSINO_MEDIO));
		assertTrue(verificar);
	}
	
	@Test
	void testarRemocao2() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.removerDisciplina("DDDDD");
		});
	}
	
	@Test
	void testarRemocao3() {
		assertThrows(NullPointerException.class, () -> {
			dao.removerDisciplina(null);
		});
	}
	
	@Test
	void alteracaoNula1() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar(null, AtributosDisciplina.NOME, "filosofia");
		});
	}
	
	@Test
	void alteracaoNula2() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar("ART01", null, "josevaldo ferreira");
		});
	}
	
	@Test
	void alteracaoNula3() {
		assertThrows(NullPointerException.class, () -> {
			dao.Atualizar("ART01", AtributosDisciplina.NOME, null);
		});
	}
	
	@Test
	void alteracaoVazia4() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.Atualizar("ART01", AtributosDisciplina.NOME, "");
		});
	}
	
	@Test
	void alteracaoCodigoInvalido() {
		assertThrows(ConsultaNulaException.class, () -> {
			dao.Atualizar("FFFFF", AtributosDisciplina.NOME, "geografia");
		});
	}
	
	@Test
	void alteracaoNome1() {
		dao.Atualizar("GEO02", AtributosDisciplina.NOME, "filosofia");
		boolean verificacao = "Filosofia".equals(dao.obterUltimo().getNome());
		dao.Atualizar("FIL02", AtributosDisciplina.NOME, "geografia");
		assertTrue(verificacao);
	}
	
	@Test
	void alteracaoNome2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.Atualizar("GEO02", AtributosDisciplina.NOME, "artes");
			
		});
	}
	
	@Test
	void alterarCargaHoraria1() {
		dao.Atualizar("GEO02", AtributosDisciplina.CARGA_HORARIA, "200");
		assertEquals(200, dao.obterUltimo().getCargaHoraria());
	}
	
	@Test
	void alterarCargaHoraria2() {
		assertThrows(NumberFormatException.class, () -> {
			dao.Atualizar("GEO02", AtributosDisciplina.CARGA_HORARIA, "asd");			
		});
	}
	
	@Test
	void alterarNivelDisciplina1() {
		dao.Atualizar("GEO02", AtributosDisciplina.NIVEL_DISCIPLINA, "fundamental");
		boolean verificacao = "Fundamental".equalsIgnoreCase(dao.obterUltimo().getNivelDaDisciplina());
		dao.Atualizar("GEO01", AtributosDisciplina.NIVEL_DISCIPLINA, "ensino medio");
		assertTrue(verificacao);
	}
	
	@Test
	void alterarNivelDisciplina2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.Atualizar("ART02", AtributosDisciplina.NIVEL_DISCIPLINA, "fundamental");			
		});
	}
	
	@Test
	void alterarNivelDisciplina3() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			dao.Atualizar("ART01", AtributosDisciplina.NIVEL_DISCIPLINA, "ensino medio");			
		});
	}
}
