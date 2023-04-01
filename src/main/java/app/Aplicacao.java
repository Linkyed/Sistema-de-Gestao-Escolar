package app; 
import java.sql.Date;

import app.modelo.Aluno;
import app.modelo.Funcionalidades;
import app.modelo.Professor;
import app.modelo.infra.AlunoDAO;
import app.modelo.infra.ProfessorDAO;

public class Aplicacao {
	public static void main(String[] args) {
		//Criando Data
		Date sqlDate = Funcionalidades.cirarDataSQL(10, 3, 2023);
				
		
		//Iniciando Tabelas
		Professor p = new Professor("Estefani Grilo Aguiar", "93774484090", "Masculino", "estefani@gmail.com", 4550.0, sqlDate);
		Aluno a = new Aluno("Thales ViTOr Costa", "05959533014", "Masculino", "thales@gmail.com", sqlDate);
		
		ProfessorDAO profDAO = new ProfessorDAO();
		AlunoDAO alunDAO = new AlunoDAO();
		
		profDAO.incluirAtomico(p);
		alunDAO.incluirAtomico(a);
		
		alunDAO.fechar();
		profDAO.fechar();
	}
}
