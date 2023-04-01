package app.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(nullable = false)
	private Integer cargaHoraria;
	
	@Column(nullable = false, length = 20)
	private String nivelDaDisciplina;

	public Disciplina() {
	}
	
	public Disciplina(AreasDeConhecimento nome, Integer cargaHoraria, NivelEscolar nvEsc) {
		setNome(nome);
		setCargaHoraria(cargaHoraria);
		setNivelDaDisciplina(nvEsc);
		setCodigo();
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
		String novoCodigo = "";
		
		if ("geografia".equalsIgnoreCase(nome)) novoCodigo = "GEO";
		else if ("artes".equalsIgnoreCase(nome)) novoCodigo = "ART";
		else if ("bioloia".equalsIgnoreCase(nome)) novoCodigo = "BIO";
		else if ("educação física".equalsIgnoreCase(nome)) novoCodigo = "EDF";
		else if ("filosofia".equalsIgnoreCase(nome)) novoCodigo = "FIL";
		else if ("história".equalsIgnoreCase(nome)) novoCodigo = "HIS";
		else if ("matemática".equalsIgnoreCase(nome)) novoCodigo = "MAT";
		else if ("química".equalsIgnoreCase(nome)) novoCodigo = "QUI";
		else if ("sociologia".equalsIgnoreCase(nome)) novoCodigo = "SOC";
		else if ("língua portuguesa".equalsIgnoreCase(nome)) novoCodigo = "LPT";
		else if ("literatura".equalsIgnoreCase(nome)) novoCodigo = "LIT";
		else if ("língua inglesa".equalsIgnoreCase(nome)) novoCodigo = "ING";
		else if ("língua alemã".equalsIgnoreCase(nome)) novoCodigo = "ALM";
		else if ("língua francesa".equalsIgnoreCase(nome)) novoCodigo = "FRA";
		
		
		if ("fundamental".equalsIgnoreCase(nivelDaDisciplina)) novoCodigo = novoCodigo + "01";
		else if ("ensino medio".equalsIgnoreCase(nivelDaDisciplina))  novoCodigo = novoCodigo + "02";
		
		this.codigo = novoCodigo;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(AreasDeConhecimento disiplina) {
		if (disiplina == null) throw new NullPointerException("Area de Formação nula.");
		
		if (disiplina.equals(AreasDeConhecimento.GEOGRAFIA)) this.nome = "Geografia";
		else if (disiplina.equals(AreasDeConhecimento.ARTES)) this.nome = "Artes";
		else if (disiplina.equals(AreasDeConhecimento.BIOLOGIA)) this.nome = "Biologia";
		else if (disiplina.equals(AreasDeConhecimento.EDUCACAO_FISICA)) this.nome = "Educação Física";
		else if (disiplina.equals(AreasDeConhecimento.FILOSOFIA)) this.nome = "Filosofia";
		else if (disiplina.equals(AreasDeConhecimento.HISTORIA)) this.nome = "História";
		else if (disiplina.equals(AreasDeConhecimento.MATEMATICA)) this.nome = "Matemática";
		else if (disiplina.equals(AreasDeConhecimento.QUIMICA)) this.nome = "Química";
		else if (disiplina.equals(AreasDeConhecimento.SOCIOLOGIA)) this.nome = "Sociologia";
		else if (disiplina.equals(AreasDeConhecimento.LINGUA_PORTUGUESA)) this.nome = "Língua Portuguesa";
		else if (disiplina.equals(AreasDeConhecimento.LITERATURA)) this.nome = "Literatura";
		else if (disiplina.equals(AreasDeConhecimento.LINGUA_INGLESA)) this.nome = "Língua Inglesa";
		else if (disiplina.equals(AreasDeConhecimento.LINGUA_ALEMA)) this.nome = "Língua Alemã";
		else if (disiplina.equals(AreasDeConhecimento.LINGUA_FRANCESA)) this.nome = "Língua Francesa";
	}


	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		if(cargaHoraria == null) throw new NullPointerException("Carga horaria nula.");
		if (cargaHoraria < 0) throw new IllegalArgumentException("Carga horaria negativa.");
		if ("fundamental".equalsIgnoreCase(nivelDaDisciplina) && cargaHoraria > 160) throw new IllegalArgumentException("Carga Horaria passou do limite.");
		if ("ensino medio".equalsIgnoreCase(nivelDaDisciplina) && cargaHoraria > 200) throw new IllegalArgumentException("Carga Horaria passou do limite.");
		this.cargaHoraria = cargaHoraria;
	}

	public String getNivelDaDisciplina() {
		return nivelDaDisciplina;
	}

	public void setNivelDaDisciplina(NivelEscolar nvEsc) {
		if(nvEsc == null) throw new NullPointerException("Nivel escolar nulo.");
		if (nvEsc.equals(NivelEscolar.FUNDAMENTAL)) {
			this.nivelDaDisciplina = "Fundamental";
		} else if (nvEsc.equals(NivelEscolar.ENSINO_MEDIO)) {
			this.nivelDaDisciplina = "Ensino Medio";
		}
	}
	
}