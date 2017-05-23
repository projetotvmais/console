package br.com.douglasfernandes.console.controller.utils;

/**
 * Representa um booleano de tres estados.
 * @author douglas.f.filho
 *
 */
public class TriState {
	private int estado = 2;
	
	public void setEstado(){
		this.estado = 2;
	}
	
	public void setEstado(boolean estado){
		if(estado)
			this.estado = 1;
		else
			this.estado = 0;
	}
	
	public int getEstado(){
		return this.estado;
	}
	
	public boolean getEstadoAsBoolean(){
		if(estado > 0)
			return true;
		else
			return false;
	}
}
