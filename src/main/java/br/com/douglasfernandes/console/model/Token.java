package br.com.douglasfernandes.console.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.douglasfernandes.console.controller.utils.FMT;
import br.com.douglasfernandes.console.controller.utils.FMT.DateFormat;

/**
 * Classe usada para testar canais por um tempo determinado.
 * @author douglas.f.filho
 *
 */
@Entity
@Table(name="tokens")
public class Token {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@OneToOne
	@JoinColumn(name = "canal", nullable = false)
	private Canal canal;
	
	@Column(name="validade", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar validade;
	
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
	public Canal getCanal() {
		return canal;
	}
	public void setCanal(Canal canal) {
		this.canal = canal;
	}
	public Calendar getValidade() {
		return validade;
	}
	public void setValidade(Calendar validade) {
		this.validade = validade;
	}
	@Override
	public String toString() {
		if(nome == null)
			nome = "<nulo>";
		if(validade == null)
			validade = FMT.getCalendarFromString("01/01/1990", DateFormat.DMY);
		
		return "Token [id=" + id + ", nome=" + nome + ", canal=" + canal.getNome() + ", validade=" + validade + "]";
	}
}
