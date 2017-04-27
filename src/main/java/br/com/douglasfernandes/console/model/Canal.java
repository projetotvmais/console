package br.com.douglasfernandes.console.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Representa o canal disponível para o cliente.
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
	@Column(name="logo", length=15000000, nullable=false)//Tamanho máximo de 15Mb
	private byte[] logo;
	@Column(name="url", nullable=false, unique=true)
	private String url;
	@Column(name="classificacao", nullable=false)
	private String classificacao;
	@OneToOne
	@JoinColumn(name = "pacote", nullable = false)
	private Pacote pacote;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Pacote getPacote() {
		return pacote;
	}
	public void setPacote(Pacote pacote) {
		this.pacote = pacote;
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
