package app.modelo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import app.model.Funcionalidades;

@Entity
@Table(name = "professores")
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 200)
	private String nome;
	
	@Column(nullable = false, length = 20)
	private String sexo;
	
	@Column(nullable = false, length = 150, unique = true)
	private String email;
	
	@Column(nullable = false, precision = 11, scale = 2)
	private Double salario;
	
	@Column(nullable = false)
	private Date inicioContrato;
	
	public Professor() {
		
	}
	
	public Professor(String nome, String sexo, String email, Double salario, Date inicioContrato) {
		this.nome = nome;
		this.sexo = sexo;
		this.email = email;
		this.salario = salario;
		this.inicioContrato = inicioContrato;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		nome = Funcionalidades.verificarStringVazia(nome);
		this.nome = Funcionalidades.primeiraLetraMaiuscula(nome.trim());
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		sexo = Funcionalidades.verificarStringVazia(sexo);
		if ("feminino".equalsIgnoreCase(sexo) || "masculino".equalsIgnoreCase(sexo) || "outro".equalsIgnoreCase(sexo)) {
			this.sexo = Funcionalidades.primeiraLetraMaiuscula(sexo);			
		} else {
			throw new IllegalArgumentException("Sexo invalido");
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		email = Funcionalidades.verificarStringVazia(email);
		if (!Funcionalidades.verificarEmail(email)) throw new IllegalArgumentException("Email invalido.");
		this.email = email;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		if (salario > 999999999.99 ) {
			throw new IllegalArgumentException("Salario ultrapassou o limite.");
		} else if(salario < 0) {
			throw new IllegalArgumentException("Salario negativo.");
		}
		this.salario = salario;
	}

	public Date getInicioContrato() {
		return inicioContrato;
	}

	public void setInicioContrato(Date inicioContrato) {
		this.inicioContrato = inicioContrato;
	}
	
	
	
}
