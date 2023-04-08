package app; 
import java.sql.Date;
import java.util.List;

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
		Professor p1 = new Professor("Estefani Grilo Aguiar", "93774484090", "Masculino", "estefani@gmail.com", "Geografia", 4550.0, sqlDate);
		Professor p2 = new Professor("Rafael Tosta", "66481727030", "Masculino", "rafael@gmail.com", "Letras com português", 4550.0, sqlDate);
		
		Disciplina d1 = new Disciplina("Geografia", 120, "fundamental", "GEO");
		Disciplina d2 = new Disciplina("LITERATURA", 180, "ensino medio", "LIT");
		Turma t = new Turma("Ensino medio", "A", "MP65");
		Aluno a = new Aluno("teste", "24581919088", "Feminino", "teste@gmail.com", sqlDate);

		//List<Professor> lista = DAOs.profDAO.consultar(ProfessorDAO.obterProfessor, "CPF", "93774484091");
		//System.out.println(lista.size());
		//DAOs.discDAO.fechar();
		//DAOs.alunDAO.fechar();
		DAOs.profDAO.fechar();
	}
}
