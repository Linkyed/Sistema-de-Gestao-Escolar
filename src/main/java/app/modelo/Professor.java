package app.modelo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private Timestamp inicioContrato;
	
	public Professor() {
		
	}
	
	public Professor(String nome, String sexo, String email, Double salario, Timestamp inicioContrato) {
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
		this.nome = nome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public Timestamp getInicioContrato() {
		return inicioContrato;
	}

	public void setInicioContrato(Timestamp inicioContrato) {
		this.inicioContrato = inicioContrato;
	}
	
	
	
}
