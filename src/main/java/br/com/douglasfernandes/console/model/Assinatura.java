package br.com.douglasfernandes.console.model;

import java.util.List;

/**
 * Representa a assinatura do usuario.
 * @author douglas.f.filho
 *
 */
public class Assinatura {
	private Pacote pacote;
	private List<Fatura> faturas;
	
	public Pacote getPacote() {
		return pacote;
	}
	public void setPacote(Pacote pacote) {
		this.pacote = pacote;
	}
	public List<Fatura> getFaturas() {
		return faturas;
	}
	public void setFaturas(List<Fatura> faturas) {
		this.faturas = faturas;
	}
}
