package br.com.douglasfernandes.console.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.codec.binary.Hex;

/**
 * Representa o canal dispon�vel para o cliente.
 * @author douglas.f.filho
 *
 */
@Entity
@Table(name = "canais")
public class Canal {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="nome", nullable=false, unique=true)
	private String nome;
	@Column(name="logo", length=15000000, nullable=false)//Tamanho m�ximo de 15Mb
	private byte[] logo;
	@Column(name="url", nullable=false, unique=true)
	private String url;
	@OneToOne
	@JoinColumn(name = "classificacao", nullable = false)
	private Classificacao classificacao;
	@Column(name="funcionando", nullable=false)
	private boolean funcionando;
	@Column(name="observacoes", length=8000)
	private String observacoes;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean getFuncionando() {
		return funcionando;
	}
	public void setFuncionando(boolean funcionando) {
		this.funcionando = funcionando;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public void setDefaultLogo() throws Exception
	{
		String hexImage = "89504E470D0A1A0A0000000D49484452000000C0000000C0080000000077B733DB00000239494441547801EDDA090AEB201446E1EE7F4F3F820409489080040948E84ADE3CCFF199780BE7ACC0AF73AFF7F17CF1000000602E0000000000000000000000000000000000000000000C08000000000000000000000000FB1A833E15E2BABF16608B4E3FE4E2F62A802379FD329F8E570024A7DFE6927940F1FA63BED8062CFA6B8B61409DF40F4DD52A6077FAA7DC6E13B03BE97EC1A3FFF95B040600D5E944AE9A034C3AD5640DB0E8648B2D40D1E98A2980D7E9BC25405243C90EE0706AC81D6600494D253300AFA6BC15C0A6C6362380A8C6A211805363CE06605773BB09C0AAE6561380A8E6A209405073C10440ED09C0F301E000C04B08407B7C91F153E23F7FCCF1739A3F341DFE52F2A79EB14AEBE75062B4C870B7D7789D0B0EAE98DA2FF9B866E5A2BB7DD580650FD66DDA179E583963E9AF7DED92C557568F6D05000000000000000000C05400009492D21C267D5308734AA59807EC3905AF3FE443CABB4D40CD4BD03F16965C4D016A8E5E27F331571B806DF16ACC2FDB68C0169DDAEB316D798C3C7D0F4333A026AF6EF9546F0694599D9BCB8D803CE982A67C13207B5D94CF3700CAA40B9BCAC5803AEBE2E67A256075BA3CB75E0638826E291CD7008AD34DB9720520EBC6727F40D4ADC5DE80A89B8B7D01AB6E6FED09D834A0AD1FE0701A903BBA01660D69EE05281A54E904081A54E803A81A56ED025835ACB50B206858A10BC06958AE0B4003EB01385E1D500000000000000000000000000000000000000000000000000000000000000000C0D7DE02BAA0BAA278A4C57C0000000049454E44AE426082";
		char[] hex = new char[hexImage.length()];
		for(int i = 0;i < hexImage.length();i++)
		{
			hex[i] = hexImage.charAt(i);
		}
		byte[] data = Hex.decodeHex(hex);
		this.logo = data;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Classificacao getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(Classificacao classificacao) {
		this.classificacao = classificacao;
	}
	@Override
	public String toString() {
		String nome = "";
		if(this.nome != null)
			nome = this.nome;
		
		int len = 0;
		if(logo != null)
			len = logo.length;
		
		String url = "";
		if(this.url != null)
			url = this.url;
		
		String classificacao = "";
		if(this.classificacao != null)
			classificacao = this.classificacao.getNome();
		
		String observacoes = "";
		if(this.observacoes != null)
			observacoes = this.observacoes;
		
		return "Canal [id=" + id + ", " + 
			   "nome=" + nome + ", " + 
				"logo=" + len + " bytes" + ", " + 
				"url=" + url + ", " + 
				"classificacao=" + classificacao + ", " +
				"funcionando=" + funcionando + ", " +
				"observacoes=" + observacoes
				+ "]";
	}
}
