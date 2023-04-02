package app; 
import java.sql.Date;

import app.excecao.RegistroDuplicadoException;
import app.modelo.Aluno;
import app.modelo.AreasDeConhecimento;
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
		Date sqlDate = Funcionalidades.cirarDataSQL(10, 3, 2023);
				
		
		//Iniciando Tabelas
		Professor p = new Professor("Estefani Grilo Aguiar", "93774484090", "Masculino", "estefani@gmail.com", AreasDeConhecimento.GEOGRAFIA, 4550.0, sqlDate);
		Aluno a = new Aluno("Thales ViTOr Costa", "05959533014", "Masculino", "thales@gmail.com", sqlDate);
		Disciplina d = new Disciplina(AreasDeConhecimento.GEOGRAFIA, 120, NivelEscolar.ENSINO_MEDIO);
		
		ProfessorDAO profDAO = new ProfessorDAO();
		AlunoDAO alunDAO = new AlunoDAO();
		DisciplinaDAO discDAO = new DisciplinaDAO();
		
		try {
			profDAO.criarProfessor(p);			
		} catch (RegistroDuplicadoException e) {
			System.out.println("Prof duplicado");
		}
		//alunDAO.incluirAtomico(a);
		//discDAO.incluirAtomico(d);
		//profDAO.removerEntidade(p);
		//System.out.println(profDAO.removerProfessor("93774484090"));
		
		discDAO.fechar();
		alunDAO.fechar();
		profDAO.fechar();
	}
}
