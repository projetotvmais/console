package br.com.douglasfernandes.console.controller.utils;

/**
 * Usado em BaseParams para auxiliar na lista de parametros.
 * @author douglas.f.filho
 *
 */
public class Param {
	private String nome;
	private Object valor;
	private Modo modo;
	
	public enum Modo{
		IGUAL(" = "),
		LIKE(" like "),
		MENOR(" < "),
		MENOR_IGUAL(" <= "),
		MAIOR(" > "),
		MAIOR_IGUAL(" >= ");
		
		private String valor;
		
		private Modo(String valor){
			this.valor = valor;
		}
		
		public String getValue(){
			return this.valor;
		}
	}
	
	private Param(){
		nome = "1";
		valor = "1";
		modo = Modo.IGUAL;
	}
	
	public static Param getInstance(){
		return new Param();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = valor;
	}

	public String getModo() {
		return modo.getValue();
	}

	public void setModo(Modo modo) {
		this.modo = modo;
	}
	
	
}
