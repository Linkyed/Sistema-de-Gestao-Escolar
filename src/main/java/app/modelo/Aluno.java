package app.modelo;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@Column(nullable = false, length = 15, unique = true)
	private String matricula;
	
	@Column(nullable = false, length = 200)
	private String nome;
	
	@Column(nullable = false, length = 11, unique = true)
	private String CPF;
	
	@Column(nullable = false, length = 10)
	private String sexo;
	
	@Column(nullable = false, length = 150)
	private String emailDoResponsavel;
	
	@Column(nullable = false)
	private Date dataNascimento;

	@ManyToOne(cascade = CascadeType.MERGE)
	private Turma turma;
	
	public Aluno() {
		
	}
	
	public Aluno(String nome, String CPF, String sexo, String email, Date dataNacimento, Turma turma) {
		setNome(nome);
		setCPF(CPF);
		setSexo(sexo);
		setEmail(email);
		setDataNascimento(dataNacimento);
		setTurma(turma);
		setMatricula();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	private void setMatricula() {
		String resultado = funcaoGerarMatricua();
		matricula = String.format("%s%s", resultado, dataNascimento.toString().replace("-", ""));
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = Funcionalidades.primeiraLetraMaiuscula(nome);
	}

	public String getCPF() {
		return CPF;
	}
	
	public void setCPF(String CPF) {
		this.CPF = Funcionalidades.verificarValidadeCPF(CPF);
		if (this.CPF != null && this.dataNascimento != null) {			
			setMatricula();
		}
	}
	
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		sexo = Funcionalidades.primeiraLetraMaiuscula(sexo);
		if ("feminino".equalsIgnoreCase(sexo) || "masculino".equalsIgnoreCase(sexo) || "outro".equalsIgnoreCase(sexo)) {
			this.sexo = Funcionalidades.primeiraLetraMaiuscula(sexo);			
		} else {
			throw new IllegalArgumentException("Sexo invalido");
		}
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
		if (this.CPF != null && this.dataNascimento != null) {			
			setMatricula();
		}
	}
	
	private String funcaoGerarMatricua() {
		int finalCPF = Integer.parseInt(CPF.substring(7));
		int outrosDigitosSoma = 0;
		
		for (int i = 0; i < 9; i++) {
			outrosDigitosSoma += Integer.parseInt(CPF.substring(i, i+1));
		}
		String funcaoMatricula = finalCPF + "" + outrosDigitosSoma;
		return funcaoMatricula;
	}

	@Override
	public int hashCode() {
		return Objects.hash(CPF, matricula);
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
		return Objects.equals(CPF, other.CPF) && Objects.equals(matricula, other.matricula);
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	
	
	
}
