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
import app.modelo.infra.DAOs;
import app.modelo.infra.DisciplinaDAO;
import app.modelo.infra.ProfessorDAO;
import app.modelo.infra.TurmaDAO;

public class Aplicacao {
	public static void main(String[] args) {
		//Criando Data
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
				
		
		//Iniciando Tabelas
		Professor p1 = new Professor("Estefani Grilo Aguiar", "93774484090", "Masculino", "estefani@gmail.com", AreasDeConhecimento.LITERATURA, 4550.0, sqlDate);
		Professor p2 = new Professor("Rafael Tosta", "66481727030", "Masculino", "rafael@gmail.com", AreasDeConhecimento.LINGUA_PORTUGUESA, 4550.0, sqlDate);
		
		Disciplina d1 = new Disciplina(AreasDeConhecimento.LITERATURA, 120, NivelEscolar.FUNDAMENTAL);
		Disciplina d2 = new Disciplina(AreasDeConhecimento.LITERATURA, 180, NivelEscolar.ENSINO_MEDIO);
		Turma t = new Turma(NivelEscolar.ENSINO_MEDIO, "A", "MP65");
		Aluno a = new Aluno("teste", "24581919088", "Feminino", "teste@gmail.com", sqlDate, null);
		
		//p2.adicionarDisciplinas(d2);
		DAOs.turmDAO.criarTurma(t);
		DAOs.alunDAO.criarAluno(a);
		DAOs.alunDAO.Atualizar("24581919088", AtributosAluno.TURMA, "EMA");
		DAOs.alunDAO.Atualizar("24581919088", AtributosAluno.TURMA, null);
		DAOs.turmDAO.removerTurma("EMA");
	
		
		DAOs.discDAO.fechar();
		DAOs.alunDAO.fechar();
		DAOs.profDAO.fechar();
	}
}
