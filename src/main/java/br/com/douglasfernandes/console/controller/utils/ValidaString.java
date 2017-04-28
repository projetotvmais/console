package br.com.douglasfernandes.console.controller.utils;

/**
 * Verifica validade de uma String que nao pode ser nula ou em branco.
 * @author douglas.f.filho
 *
 */
public class ValidaString {
	public static boolean isValida(String string){
		if(string != null && !string.equals(null) && !string.equals(""))
			return true;
		else
			return false;
	}
}
