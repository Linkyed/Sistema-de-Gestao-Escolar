package app.modelo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

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
	
	@Column(nullable = false, length = 11, unique = true)
	private String CPF;
	
	@Column(nullable = false, length = 10)
	private String sexo;
	
	@Column(nullable = false, length = 150, unique = true)
	private String email;
	
	@Column(nullable = false, length = 80)
	private String areaDeFormacao;
	
	@Column(nullable = false, precision = 11, scale = 2)
	private Double salario;
	
	@Column(nullable = false)
	private Date inicioContrato;
	
	public Professor() {
		
	}
	
	public Professor(String nome, String CPF, String sexo, String email, AreasDeConhecimento areaDeFormacao, Double salario, Date inicioContrato) {
		setNome(nome);
		setCPF(CPF);
		setSexo(sexo);
		setEmail(email);
		setSalario(salario);
		setAreaDeFormacao(areaDeFormacao);
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
		this.nome = Funcionalidades.primeiraLetraMaiuscula(nome);
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
		sexo = Funcionalidades.primeiraLetraMaiuscula(sexo);
		if ("feminino".equalsIgnoreCase(sexo) || "masculino".equalsIgnoreCase(sexo) || "outro".equalsIgnoreCase(sexo)) {
			this.sexo = sexo;			
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

	public String getAreaDeFormacao() {
		return areaDeFormacao;
	}

	public void setAreaDeFormacao(AreasDeConhecimento areaDeFormacao) {
		if (areaDeFormacao == null) throw new NullPointerException("Area de Formação nula.");
		
		if (areaDeFormacao.equals(AreasDeConhecimento.GEOGRAFIA)) this.areaDeFormacao = "Geografia";
		else if (areaDeFormacao.equals(AreasDeConhecimento.ARTES)) this.areaDeFormacao = "Artes";
		else if (areaDeFormacao.equals(AreasDeConhecimento.BIOLOGIA)) this.areaDeFormacao = "Biologia";
		else if (areaDeFormacao.equals(AreasDeConhecimento.EDUCACAO_FISICA)) this.areaDeFormacao = "Educação Física";
		else if (areaDeFormacao.equals(AreasDeConhecimento.FILOSOFIA)) this.areaDeFormacao = "Filosofia";
		else if (areaDeFormacao.equals(AreasDeConhecimento.HISTORIA)) this.areaDeFormacao = "História";
		else if (areaDeFormacao.equals(AreasDeConhecimento.MATEMATICA)) this.areaDeFormacao = "Matemática";
		else if (areaDeFormacao.equals(AreasDeConhecimento.FISICA)) this.areaDeFormacao = "Física";
		else if (areaDeFormacao.equals(AreasDeConhecimento.QUIMICA)) this.areaDeFormacao = "Química";
		else if (areaDeFormacao.equals(AreasDeConhecimento.SOCIOLOGIA)) this.areaDeFormacao = "Sociologia";
		else if (areaDeFormacao.equals(AreasDeConhecimento.LINGUA_PORTUGUESA)) this.areaDeFormacao = "Língua Portuguesa";
		else if (areaDeFormacao.equals(AreasDeConhecimento.LITERATURA)) this.areaDeFormacao = "Literatura";
		else if (areaDeFormacao.equals(AreasDeConhecimento.LINGUA_INGLESA)) this.areaDeFormacao = "Língua Inglesa";
		else if (areaDeFormacao.equals(AreasDeConhecimento.LINGUA_ALEMA)) this.areaDeFormacao = "Língua Alemã";
		else if (areaDeFormacao.equals(AreasDeConhecimento.LINGUA_FRANCESA)) this.areaDeFormacao = "Língua Francesa";
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
	
	static public AreasDeConhecimento formacaoString(String areConhe) {
		if (areConhe == null) throw new NullPointerException("Area de Formação nula.");
		
		if ("geografia".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.GEOGRAFIA;
		else if ("artes".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.ARTES;
		else if ("biologia".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.BIOLOGIA;
		else if ("educação física".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.EDUCACAO_FISICA;
		else if ("filosofia".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.FILOSOFIA;
		else if ("história".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.HISTORIA;
		else if ("matemática".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.MATEMATICA;
		else if ("física".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.FISICA;
		else if ("química".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.QUIMICA;
		else if ("sociologia".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.SOCIOLOGIA;
		else if ("língua portuguesa".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.LINGUA_PORTUGUESA;
		else if ("literatura".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.LITERATURA;
		else if ("língua inglesa".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.LINGUA_INGLESA;
		else if ("língua alemã".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.LINGUA_ALEMA;
		else if ("língua francesa".equalsIgnoreCase(areConhe)) return AreasDeConhecimento.LINGUA_FRANCESA;
		else {
			return null;
		}
	}
	
}
