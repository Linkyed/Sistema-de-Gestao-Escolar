package app; 
import java.sql.Date;

import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.AreasDeConhecimento;
import app.modelo.AtributosAluno;
import app.modelo.AtributosDisciplina;
import app.modelo.AtributosProfessor;
import app.modelo.Disciplina;
import app.modelo.Funcionalidades;
import app.modelo.NivelEscolar;
import app.modelo.Professor;
import app.modelo.infra.AlunoDAO;
import app.modelo.infra.DisciplinaDAO;
import app.modelo.infra.ProfessorDAO;

public class Aplicacao {
	public static void main(String[] args) {
		//Criando Data
		Date sqlDate = Funcionalidades.cirarDataSQL("28-02-2023");
				
		
		//Iniciando Tabelas
		Professor p = new Professor("Estefani Grilo Aguiar", "93774484090", "Masculino", "estefani@gmail.com", AreasDeConhecimento.GEOGRAFIA, 4550.0, sqlDate);
		Aluno a = new Aluno("teste", "24581919088", "Feminino", "teste@gmail.com", sqlDate);
		Disciplina d = new Disciplina(AreasDeConhecimento.GEOGRAFIA, 120, NivelEscolar.ENSINO_MEDIO);
		
		ProfessorDAO profDAO = new ProfessorDAO();
		AlunoDAO alunDAO = new AlunoDAO();
		DisciplinaDAO discDAO = new DisciplinaDAO();
		

		discDAO.criarDisciplina(new Disciplina(AreasDeConhecimento.MATEMATICA, 120, NivelEscolar.FUNDAMENTAL));
		
		System.out.println(discDAO.Atualizar("MAT01", AtributosDisciplina.NOME, "filosofia").getNome());
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
