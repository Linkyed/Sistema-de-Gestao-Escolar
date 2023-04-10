package app.modelo;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alunos")
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 200)
	private String nome;
	
	@Column(nullable = false, length = 11, unique = true)
	private String CPF;
	
	@Column(nullable = false, length = 10)
	private String sexo;
	
	@Column(name = "email_do_responsavel", nullable = false, length = 150)
	private String emailDoResponsavel;
	
	@Column(name = "data_nascimento", nullable = false)
	private Date dataNascimento;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Turma turma;
	
	public Aluno() {
		
	}
	
	public Aluno(String nome, String CPF, String sexo, String email, Date dataNacimento) {
		setNome(nome);
		setCPF(CPF);
		setSexo(sexo);
		setEmail(email);
		setDataNascimento(dataNacimento);
	}
	
	public Aluno(String nome, String CPF, String sexo, String email, Date dataNacimento, Turma turma) {
		setNome(nome);
		setCPF(CPF);
		setSexo(sexo);
		setEmail(email);
		setTurma(turma);
		setDataNascimento(dataNacimento);
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
		return emailDoResponsavel;
	}

	public void setEmail(String email) {
		this.emailDoResponsavel = Funcionalidades.verificarEmail(email);
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNacimento) {
		this.dataNascimento = (Date) Funcionalidades.testarObjetoNulo.apply(dataNacimento);
	}
	
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		if (turma == null) {
			if (this.turma != null) this.turma.removerAluno(this);
		} else {
			if (this.turma != null) this.turma.removerAluno(this);
			turma.adicionarAluno(this);
		}
		this.turma = turma;
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
		Aluno other = (Aluno) obj;
		return Objects.equals(CPF, other.CPF);
	}
	
}
