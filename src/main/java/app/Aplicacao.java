package app; 
import java.sql.Date;

import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.AreasDeConhecimento;
import app.modelo.AtributosAluno;
import app.modelo.AtributosDisciplina;
import app.modelo.AtributosProfessor;
import app.modelo.AtributosTurma;
import app.modelo.Disciplina;
import app.modelo.Funcionalidades;
import app.modelo.NivelEscolar;
import app.modelo.Professor;
import app.modelo.Turma;
import app.modelo.infra.AlunoDAO;
import app.modelo.infra.DisciplinaDAO;
import app.modelo.infra.ProfessorDAO;
import app.modelo.infra.TurmaDAO;

public class Aplicacao {
	public static void main(String[] args) {
		//Criando Data
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
				
		
		//Iniciando Tabelas
		Professor p1 = new Professor("Estefani Grilo Aguiar", "93774484090", "Masculino", "estefani@gmail.com", AreasDeConhecimento.LITERATURA, 4550.0, sqlDate);
		Professor p2 = new Professor("Rafael Tosta", "24581919088", "Masculino", "rafael@gmail.com", AreasDeConhecimento.LINGUA_PORTUGUESA, 4550.0, sqlDate);
		
		Disciplina d1 = new Disciplina(AreasDeConhecimento.LITERATURA, 120, NivelEscolar.FUNDAMENTAL);
		Disciplina d2 = new Disciplina(AreasDeConhecimento.LITERATURA, 180, NivelEscolar.ENSINO_MEDIO);
		Turma t = new Turma(NivelEscolar.ENSINO_MEDIO, "B", "MP65");
		Aluno a = new Aluno("teste", "24581919088", "Feminino", "teste@gmail.com", sqlDate, t);
		
		//p2.adicionarDisciplinas(d2);
		
		ProfessorDAO profDAO = new ProfessorDAO();
		AlunoDAO alunDAO = new AlunoDAO();
		DisciplinaDAO discDAO = new DisciplinaDAO();
		TurmaDAO turmDAO = new TurmaDAO();
		
		//alunDAO.Atualizar("24581919088", AtributosAluno.TURMA, "EMA");
		turmDAO.Atualizar("EMB", AtributosTurma.ALUNOS_REMOVER, "24581919088");
		//turmDAO.criarTurma(t);
		//alunDAO.criarAluno(a);
		//turmDAO.removerTurma("EMB");
		//discDAO.criarDisciplina(d1);
		//discDAO.criarDisciplina(d2);
		//profDAO.Atualizar("93774484090", AtributosProfessor.DISCIPLINAS, "LIT01");
		//profDAO.getProfessorPorCPF("93774484090").adicionarDisciplinas(discDAO.getDisciplinaPorCodigo("LIT01"));
		//discDAO.removerDisciplina("LIT01");
		//p1.adicionarDisciplinas(discDAO.obterPrimeiro());
		//p1.adicionarDisciplinas(discDAO.	obterUltimo());
		//profDAO.removerProfessor("93774484090");
		//profDAO.criarProfessor(p1);
		//alunDAO.criarAluno(a);
		//profDAO.criarProfessor(p);
		//System.out.println(alunDAO.getAlunoPorMatricula("664320230228").getNome());
		//System.out.println(profDAO.getProfessorPorEmail("estefani@gmail.com").getNome());
		//discDAO.incluirAtomico(d);
		//profDAO.removerEntidade(p);
		//System.out.println(profDAO.removerProfessor("93774484090"));
		
		discDAO.fechar();
		alunDAO.fechar();
		profDAO.fechar();
	}
}
