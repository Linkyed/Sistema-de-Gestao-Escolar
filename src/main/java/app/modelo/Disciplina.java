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
import javax.persistence.Table;

@Entity
@Table(name = "disciplinas")
public class Disciplina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 5)
	private String codigo;
	
	@Column(nullable = false, length = 50)
	private String nome;
	
	@Column(name = "carga_horaria", nullable = false)
	private Integer cargaHoraria;
	
	@Column(name = "nivel_escolar", nullable = false, length = 20)
	private String nivelEscolar;
	
	public Disciplina() {
	}
	
	public Disciplina(String nome, Integer cargaHoraria, String nivelEscolar, String codigo) {
		setNome(nome);
		setCargaHoraria(cargaHoraria);
		setNivelDaDisciplina(nivelEscolar);
		setCodigo(codigo);
	}
	
	//Usado para fazer uma copia
	public Disciplina(Disciplina outraDisciplina) {
		 	setNome(outraDisciplina.getNome());
			setCargaHoraria(outraDisciplina.getCargaHoraria());
			setNivelDaDisciplina(outraDisciplina.getNivelDaDisciplina());
			setCodigo(outraDisciplina.getCodigo().substring(0, 3));
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

	public void setCodigo(String codigo) {
		codigo = Funcionalidades.testarStringNula
				.andThen(Funcionalidades.testarStringVazia)
				.andThen(Funcionalidades.testarSoLetras).apply(codigo);
		
		if (codigo.length() == 3) {
			codigo = codigo.toUpperCase();
		} else {
			throw new IllegalArgumentException("Codigo invalido.");
		}
		
		if ("fundamental".equalsIgnoreCase(nivelEscolar)) codigo = codigo + "01";
		else if ("ensino medio".equalsIgnoreCase(nivelEscolar))  codigo = codigo + "02";
		
		this.codigo = codigo;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = Funcionalidades.todaPrimeiraLetraMaiuscula(nome);
	}


	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		Funcionalidades.testarObjetoNulo.apply(cargaHoraria);
		if (cargaHoraria < 0) throw new IllegalArgumentException("Carga horaria negativa.");
		if ("fundamental".equalsIgnoreCase(nivelEscolar) && cargaHoraria > 160) throw new IllegalArgumentException("Carga Horaria passou do limite.");
		if ("ensino medio".equalsIgnoreCase(nivelEscolar) && cargaHoraria > 200) throw new IllegalArgumentException("Carga Horaria passou do limite.");
		this.cargaHoraria = cargaHoraria;
	}

	public String getNivelDaDisciplina() {
		return nivelEscolar;
	}

	public void setNivelDaDisciplina(String nivelEscolar) {
		nivelEscolar = Funcionalidades.todaPrimeiraLetraMaiuscula(nivelEscolar);
		if ("fundamental".equalsIgnoreCase(nivelEscolar) || "ensino medio".equalsIgnoreCase(nivelEscolar)) 
			this.nivelEscolar = nivelEscolar;
		else
			throw new IllegalArgumentException("Nivel escolar invalido.");
		if (codigo != null) {
			setCodigo(codigo.substring(0, 3));
		}
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
		Disciplina other = (Disciplina) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
}
