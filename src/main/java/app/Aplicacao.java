package app;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import app.modelo.Professor;
import app.modelo.infra.ProfessorDAO;

public class Aplicacao {
	public static void main(String[] args) throws ParseException {
		//Iniciando Tabelas
		DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date data = formato.parse("17/10/2002");
		Timestamp salvar = new Timestamp(data.getTime());
		
		Professor p = new Professor("Estefani Grilo Aguiar", "Masculino", "estefani@gmail.com", 4550.0, salvar);
		ProfessorDAO dao = new ProfessorDAO();
		
		dao.incluirAtomico(p);
		
		dao.fechar();
	}
}
