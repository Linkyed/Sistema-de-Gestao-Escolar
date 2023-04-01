package app.modelo;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(nullable = false, length = 150, unique = true)
	private String email;
	
	@Column(nullable = false)
	private Date dataNacimento;

	public Aluno() {
		
	}
	
	public Aluno(String nome, String CPF, String sexo, String email, Date dataNacimento) {
		setNome(nome);
		setCPF(CPF);
		setSexo(sexo);
		setEmail(email);
		setDataNacimento(dataNacimento);
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
		matricula = String.format("%s%s", resultado, dataNacimento.toString().replace("-", ""));
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		nome = Funcionalidades.verificarStringVazia(nome);
		this.nome = Funcionalidades.primeiraLetraMaiuscula(nome.trim());
	}

	public String getCPF() {
		return CPF;
	}
	
	public void setCPF(String CPF) {
		this.CPF = Funcionalidades.verificarValidadeCPF(CPF);
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
		this.email = email.toLowerCase();
	}

	public Date getDataNacimento() {
		return dataNacimento;
	}

	public void setDataNacimento(Date dataNacimento) {
		this.dataNacimento = dataNacimento;
	}
	
	private String funcaoGerarMatricua() {
		int finalCPF = Integer.parseInt(CPF.substring(9));
		int outrosDigitosSoma = 0;
		
		for (int i = 0; i < 9; i++) {
			outrosDigitosSoma += Integer.parseInt(CPF.substring(i, i+1));
		}
		String funcaoMatricula = finalCPF + "" + outrosDigitosSoma;
		return funcaoMatricula;
	}
}
