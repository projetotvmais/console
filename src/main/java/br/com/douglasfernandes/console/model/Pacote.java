package br.com.douglasfernandes.console.model;

import java.util.List;

import br.com.douglasfernandes.console.controller.utils.FMT;

/**
 * Representa o pacote de canais que o usuario contratou
 * @author douglas.f.filho
 *
 */
public class Pacote {
	private String nome;
	private byte[] logo;
	private double valor;
	private List<Canal> canais;
	
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
	public List<Canal> getCanais() {
		return canais;
	}
	public void setCanais(List<Canal> canais) {
		this.canais = canais;
	}
}
