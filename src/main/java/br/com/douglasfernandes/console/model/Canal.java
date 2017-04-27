package br.com.douglasfernandes.console.model;

/**
 * Representa o canal disponível para o cliente.
 * @author douglas.f.filho
 *
 */
public class Canal {
	private String nome;
	private byte[] logo;
	private String url;
	private String classificacao;
	
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
}
