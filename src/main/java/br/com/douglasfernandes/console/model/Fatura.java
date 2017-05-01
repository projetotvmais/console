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
 * Representa a fatura a ser paga pelo usuário.
 * @author douglas.f.filho
 *
 */
@Entity
@Table(name = "faturas")
public class Fatura {
	@Id
	@GeneratedValue
	private long id;
	@Column(name="codigo", nullable=false, unique=true)
	private String codigo;
	@Column(name="vencimento", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar vencimento;
	@Column(name="pago", nullable=false)
	private boolean pago;
	@OneToOne
	@JoinColumn(name = "pacote", nullable = false)
	private Pacote pacote;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getFmtVencimento(){
		return FMT.getStringFromCalendar(this.vencimento, DateFormat.DMY);
	}
	public Calendar getVencimento() {
		return vencimento;
	}
	public void setVencimento(Calendar vencimento) {
		this.vencimento = vencimento;
	}
	public boolean getPago() {
		return pago;
	}
	public void setPago(boolean pago) {
		this.pago = pago;
	}
	public Pacote getPacote() {
		return pacote;
	}
	public void setPacote(Pacote pacote) {
		this.pacote = pacote;
	}
}
