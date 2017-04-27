package br.com.douglasfernandes.console.controller.utils;

/**
 * Auxilia na valida��o de um e-email
 * @author Compliance Software (By Douglas Fernandes) {@link douglasf.filho@gmail.com}
 *
 */
public class ValidaEmail 
{
	public static boolean isValido(String email)
	{
		if(c(email,"@"))
		{
			String[] seq = new String[]{".com",".net",".gov",".ind",".br",".info",".org"};
			if(c(email,seq))
			{
				return true;
			}
			System.out.println("E-mail n�o cont�m extens�o v�lida.");
			return false;
		}
		else
		{
			System.out.println("E-mail n�o cont�m '@'");
			return false;
		}
	}
	
	/**
	 * Verifica se o email passado por parametro contem determinada sequencia em seu conteudo
	 * @param email
	 * @param sequencia
	 * @return
	 */
	private static boolean c(String email, String sequencia)
	{
		return email.contains(sequencia);
	}
	
	/**
	 * Verifica se o email passado por parametro contem determinadas sequencias em seu conteudo
	 * @param email
	 * @param sequencias
	 * @return
	 */
	private static boolean c(String email, String[] sequencias)
	{
		for(String s:sequencias)
		{
			if(email.contains(s))
				return true;
		}
		return false;
	}
}
