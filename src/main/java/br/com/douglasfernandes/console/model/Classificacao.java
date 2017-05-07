package br.com.douglasfernandes.console.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a classificacao de um canal
 * @author douglas.f.filho
 *
 */
@Entity
@Table(name = "classificacoes")
public class Classificacao {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="nome", nullable=false, unique=true)
	private String nome;
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
	@Override
	public String toString() {
		return "Classificacao [id=" + id + ", nome=" + nome + "]";
	}
}
