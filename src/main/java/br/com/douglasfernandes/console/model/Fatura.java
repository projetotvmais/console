package br.com.douglasfernandes.console.model;

import java.util.Calendar;

import br.com.douglasfernandes.console.controller.utils.FMT;
import br.com.douglasfernandes.console.controller.utils.FMT.DateFormat;

/**
 * Representa a fatura a ser paga pelo usuário.
 * @author douglas.f.filho
 *
 */
public class Fatura {
	private String codigo;
	private Calendar vencimento;
	private boolean pago;
	
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
}
