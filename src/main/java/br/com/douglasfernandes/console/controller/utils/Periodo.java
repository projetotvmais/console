package br.com.douglasfernandes.console.controller.utils;

import java.util.Calendar;

import br.com.douglasfernandes.console.controller.utils.FMT.DateFormat;

/**
 * Representa um range entre datas
 * @author douglas.f.filho
 *
 */
public class Periodo {
	private Calendar dataInicial;
	private Calendar dataFinal;
	private Intervalo intervalo;
	
	public enum Intervalo{
		A_PARTIR_DE("a partir de"),
		ATE("ate"),
		ENTRE("entre");
		
		private String value;
		private Intervalo(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
	
	
	private Periodo(){
		dataInicial = FMT.getAgora();
		dataFinal = null;
		intervalo = Intervalo.A_PARTIR_DE;
	}
	
	public Periodo getInstance(){
		return new Periodo();
	}

	public Calendar getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Calendar dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Calendar getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Calendar dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Intervalo getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Intervalo intervalo) {
		this.intervalo = intervalo;
	}

	@Override
	public String toString() {
		if(intervalo == Intervalo.A_PARTIR_DE){
			return "Periodo [" + intervalo.getValue() + " " + FMT.getStringFromCalendar(dataInicial, DateFormat.DMY)+"]";
		}
		else if(intervalo == Intervalo.ATE){
			return "Periodo [" + intervalo.getValue() + " " + FMT.getStringFromCalendar(dataFinal, DateFormat.DMY)+"]";
		}
		else{
			return "Periodo [" + intervalo.getValue() + " " + FMT.getStringFromCalendar(dataInicial, DateFormat.DMY)+" ate " + FMT.getStringFromCalendar(dataFinal, DateFormat.DMY) + "]";
		}
	}
	
}
