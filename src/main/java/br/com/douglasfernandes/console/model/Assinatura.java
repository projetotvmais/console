package br.com.douglasfernandes.console.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a assinatura do usuario.
 * @author douglas.f.filho
 *
 */
@Entity
@Table(name = "assinaturas")
public class Assinatura {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="pacote", nullable=false)
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
}
