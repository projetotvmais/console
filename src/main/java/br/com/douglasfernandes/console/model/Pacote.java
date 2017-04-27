package br.com.douglasfernandes.console.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.douglasfernandes.console.controller.utils.FMT;

/**
 * Representa o pacote de canais que o usuario contratou
 * @author douglas.f.filho
 *
 */
@Entity
@Table(name = "pacotes")
public class Pacote {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="nome", nullable=false, unique=true)
	private String nome;
	@Column(name="logo", length=15000000, nullable=false)//Tamanho máximo de 15Mb
	private byte[] logo;
	@Column(name="valor", nullable=false)
	private double valor;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getFmtValor(){
		return FMT.formatAsMoney(this.valor);
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
}
