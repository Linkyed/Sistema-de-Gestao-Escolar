package app.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "turmas")
public class Turma {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String codigo;
	
	@Column(name = "nivel_escolar", nullable = false, length = 25)
	private String nivelEscolar;
	
	@Column(name = "letra_turma", nullable = false, length = 1)
	private String letraTurma;
	
	@Column(nullable = false, length = 80)
	private String sala;

	public Turma() {
		
	}
	
	public Turma(String nivelEscolar, String letraTurma, String sala) {
		setNivelEscolar(nivelEscolar);
		setLetraTurma(letraTurma);
		setSala(sala);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	private void setCodigo() {
		Funcionalidades.testarObjetoNulo.apply(nivelEscolar);
		Funcionalidades.testarObjetoNulo.apply(letraTurma);
		
		String primeiraParte = "";
		if ("ensino medio".equals(nivelEscolar)) {
			primeiraParte = "EM";
		} else if ("fundamental".equals(nivelEscolar)) {
			primeiraParte = "EF";
		}
		
		this.codigo = String.format("%s%s", primeiraParte, letraTurma);
		
	}

	public String getNivelEscolar() {
		return nivelEscolar;
	}

	public void setNivelEscolar(String nivelEscolar) {
		nivelEscolar = Funcionalidades.todaPrimeiraLetraMaiuscula(nivelEscolar);
		if ("fundamental".equalsIgnoreCase(nivelEscolar) || "ensino medio".equalsIgnoreCase(nivelEscolar)) 
			this.nivelEscolar = nivelEscolar;
		if (letraTurma != null) {
			setCodigo();			
		}
	}

	public String getLetraTurma() {
		return letraTurma;
	}

	public void setLetraTurma(String letraTurma) {
		letraTurma = Funcionalidades.testarStringNula
				.andThen(Funcionalidades.testarStringVazia)
				.andThen(Funcionalidades.testarSoLetras).apply(letraTurma);
		if (letraTurma.length() != 1) throw new IllegalArgumentException("Letra da turma só pode conter 1 caracter.");
		this.letraTurma = letraTurma.toUpperCase();
		
		if (nivelEscolar != null) {
			setCodigo();			
		}
	}

	public String getSala() {
		return sala;
	}

	public void setSala(String sala) {
		sala = Funcionalidades.testarStringNula
			.andThen(Funcionalidades.testarStringVazia).apply(sala);
		this.sala = sala;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
	
}
