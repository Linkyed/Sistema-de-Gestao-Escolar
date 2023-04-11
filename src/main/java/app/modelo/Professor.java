package app.modelo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "professores")
public class Professor{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 200)
	private String nome;
	
	@Column(nullable = false, length = 11, unique = true)
	private String CPF;
	
	@Column(nullable = false, length = 10)
	private String sexo;
	
	@Column(nullable = false, length = 150)
	private String email;
	
	@Column(nullable = false, length = 80)
	private String formacao;
	
	@Column(nullable = false, precision = 11, scale = 2)
	private Double salario;
	
	@Column(name = "inicio_contrato", nullable = false)
	private Date inicioContrato;
	
	@OneToMany(mappedBy = "professor", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
	private List<Aula> aulas;
	
	
	public Professor() {
		
	}
	
	public Professor(String nome, String CPF, String sexo, String email, String formacao, Double salario, Date inicioContrato) {
		setNome(nome);
		setCPF(CPF);
		setSexo(sexo);
		setEmail(email);
		setSalario(salario);
		setFormacao(formacao);
		setInicioContrato(inicioContrato);
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
		this.nome = Funcionalidades.todaPrimeiraLetraMaiuscula(nome);
	}
	
	public String getCPF() {
		return CPF;
	}
	
	public void setCPF(String CPF) {
		this.CPF = Funcionalidades.validarCPF(CPF);
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = Funcionalidades.verificarSexo(sexo);	
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = Funcionalidades.verificarEmail(email);
	}

	public String getFormacao() {
		return formacao;
	}

	public void setFormacao(String Formacao) {	
		this.formacao = Funcionalidades.apenasPrimeiraLetraMaiscula(Formacao);
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		salario = (Double) Funcionalidades.testarObjetoNulo.apply(salario);
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
		this.inicioContrato = (Date) Funcionalidades.testarObjetoNulo.apply(inicioContrato);
	}
	
	

	public List<Aula> getAulas() {
		if (aulas == null) aulas = new ArrayList<>();
		return aulas;
	}

	public void adicionarAula(Aula aula) {
		Funcionalidades.testarObjetoNulo.apply(aula);
		if (!getAulas().contains(aula)) {
			getAulas().add(aula);
			aula.setProfessor(this);			
		}
	}
	
	public void removerAula(Aula aula) {
		Funcionalidades.testarObjetoNulo.apply(aula);
		if (getAulas().contains(aula))
			getAulas().remove(aula);
	}

	@Override
	public int hashCode() {
		return Objects.hash(CPF);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Professor other = (Professor) obj;
		return Objects.equals(CPF, other.CPF);
	}
	
	
}
