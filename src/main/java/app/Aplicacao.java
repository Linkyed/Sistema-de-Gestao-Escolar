package app;

import java.text.ParseException;
import java.util.Calendar;
import java.sql.Date;

import app.model.Funcionalidades;
import app.modelo.Professor;
import app.modelo.infra.ProfessorDAO;

public class Aplicacao {
	public static void main(String[] args) throws ParseException {
		//Criando Data
		Date sqlDate = Funcionalidades.cirarDataSQL(10, 3, 2023);
				
		
		//Iniciando Tabelas
		Professor p = new Professor("Estefani Grilo Aguiar", "Masculino", "estefani@gmail.com", 4550.0, sqlDate);
		ProfessorDAO dao = new ProfessorDAO();
		
		dao.incluirAtomico(p);
		
		dao.fechar();
	}
}
