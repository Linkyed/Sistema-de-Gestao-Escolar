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
import app.modelo.infra.DAOs;
import app.modelo.infra.DisciplinaDAO;
import app.modelo.infra.ProfessorDAO;

public class DisciplinaDAOTeste {
	Disciplina disc1;
	Disciplina disc2;
	Disciplina disc3;
	
	@BeforeEach
	void inicializarDAOeDisciplina() {
		disc3 = new Disciplina(AreasDeConhecimento.ARTES, 120, NivelEscolar.FUNDAMENTAL);
		disc2 = new Disciplina(AreasDeConhecimento.ARTES, 120, NivelEscolar.ENSINO_MEDIO);
		disc1 = new Disciplina(AreasDeConhecimento.GEOGRAFIA, 120, NivelEscolar.ENSINO_MEDIO);
		DAOs.discDAO.criarDisciplina(disc3);
		DAOs.discDAO.criarDisciplina(disc2);
		DAOs.discDAO.criarDisciplina(disc1);
	}
	
	@AfterEach
	void removerDisciplina() {
		try {
			DAOs.discDAO.removerDisciplina("ART01");
			DAOs.discDAO.removerDisciplina("ART02");
			DAOs.discDAO.removerDisciplina("GEO02");
		} catch (Exception e) {
			
		}
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
		DAOs.discDAO.criarDisciplina(new Disciplina(AreasDeConhecimento.GEOGRAFIA, 120, NivelEscolar.ENSINO_MEDIO));
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
		assertThrows(NullPointerException.class, () -> {
			DAOs.discDAO.removerDisciplina(null);
		});
	}
	
	@Test
	void testarRemocaoComProfessor() {
		DAOs.profDAO.criarProfessor(new Professor("Estefani Grilo Aguiar", "81544136048", "Masculino", "estefanitestezada@gmail.com",
				AreasDeConhecimento.LITERATURA, 4550.0, Funcionalidades.cirarDataSQL("12-10-2002")));
		DAOs.profDAO.Atualizar("81544136048", AtributosProfessor.DISCIPLINAS_ADICIONAR, "GEO02");
		Disciplina d = DAOs.discDAO.removerDisciplina("GEO02");
		boolean verificar = d.equals(disc1);
		DAOs.discDAO.criarDisciplina(new Disciplina(AreasDeConhecimento.GEOGRAFIA, 120, NivelEscolar.ENSINO_MEDIO));
		assertEquals(0, DAOs.profDAO.getProfessorPorCPF("81544136048").getDisciplinas().size());
		assertTrue(verificar);
		DAOs.profDAO.removerProfessor("81544136048");
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
	void alteracaoVazia4() {
		assertThrows(IllegalArgumentException.class, () -> {
			DAOs.discDAO.Atualizar("ART01", AtributosDisciplina.NOME, "");
		});
	}
	
	@Test
	void alteracaoCodigoInvalido() {
		assertThrows(ConsultaNulaException.class, () -> {
			DAOs.discDAO.Atualizar("FFFFF", AtributosDisciplina.NOME, "geografia");
		});
	}
	
	@Test
	void alteracaoNome1() {
		DAOs.discDAO.Atualizar("GEO02", AtributosDisciplina.NOME, "filosofia");
		boolean verificacao = "Filosofia".equals(DAOs.discDAO.obterUltimo().getNome());
		DAOs.discDAO.Atualizar("FIL02", AtributosDisciplina.NOME, "geografia");
		assertTrue(verificacao);
	}
	
	@Test
	void alteracaoNome2() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.discDAO.Atualizar("GEO02", AtributosDisciplina.NOME, "artes");
			
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
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.discDAO.Atualizar("ART02", AtributosDisciplina.NIVEL_DISCIPLINA, "fundamental");			
		});
	}
	
	@Test
	void alterarNivelDisciplina3() {
		assertThrows(RegistroDuplicadoException.class, () -> {
			DAOs.discDAO.Atualizar("ART01", AtributosDisciplina.NIVEL_DISCIPLINA, "ensino medio");			
		});
	}
}
