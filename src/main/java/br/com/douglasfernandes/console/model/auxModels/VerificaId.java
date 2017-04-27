package br.com.douglasfernandes.console.model.auxModels;

/**
 * Verifica se o cpf ou cnpj de um determinado campo est� de acordo com a legisla��o.
 * @author Compliance Software (by Douglas Fernandes)
 *
 */
public class VerificaId 
{
	
	/**
	 * M�todo universal de verifica��o de cnpj e cpf
	 * @param codigo
	 * @return
	 */
	public static boolean verifica(String codigo)
	{
		if(codigo == null || codigo.equals(null) || codigo.equals("") || codigo.length() < 11)
			return false;
		else
		{
			boolean verifica = verificaCNPJ(codigo);
			if(!verifica)
				verifica = verificaCPF(codigo);
			return verifica;
		}
	}
//----------------------------------------------------------------------CNPJ---------------------------------------------------------------------------------
	/**
	 * M�tod que informa se o cnpj de uma empresa est� correto.
	 * @param cnpj = String com o cnpj no modo num�rico.
	 * @return "true" ou "false" se est�, ou n�o, correto.
	 */
	private static boolean verificaCNPJ(String codigo)
	{
		String cnpj = codigo.replace(".", "");
		cnpj = cnpj.replace("-", "");
		cnpj = cnpj.replace("/", "");
		cnpj = cnpj.trim();
		
		try
		{
			if(cnpj.length() != 14)
			{
				System.out.println("Tamanho de cnpj errado! {atual: "+cnpj.length()+" | correto: 14}");
				return false;
			}
			else
			{
				if(soNumeros(cnpj))
				{
					if(terceiroDigitoCnpjOk(cnpj))
					{
						if(cnpjOk(cnpj))
							return true;
						else
						{
							return false;
						}
					}
					else
					{
						System.out.println("Erro ao calcular o terceiro digito de cnpj.");
						return false;
					}
				}
				else
				{
					System.out.println("O cnpj n�o cont�m apenas n�meros!{c�digo atual: "+cnpj+"}");
					return false;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Erro na execu��o do c�digo de valida��o de cnpj.");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * M�todo que informa se a String cont�m apenas n�meros.
	 * @param codigo = C�digo num�rico em forma de String.
	 * @return "true" ou "false" se cont�m apenas n�meros, ou n�o.
	 */
	private static boolean soNumeros(String codigo)
	{
		try
		{
			int i = 0;
			for(i = 0; i < codigo.length(); i++)
			{
				if(!Character.isDigit(codigo.charAt(i)))
					return false;
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	/**
	 * M�todo que verifica se o terceiro d�gito do cnpj est� correto.
	 * @param cnpj
	 * @return "true" ou "false" se estiver, ou n�o, correto.
	 */
	private static boolean terceiroDigitoCnpjOk(String codigo)
	{
		try
		{
			String cnpj = codigo.substring(0,8);
			String terceiro = ""+cnpj.charAt(7);
			cnpj = cnpj.substring(0,7);
			
			int val1 = Integer.parseInt(""+cnpj.charAt(0));
			int val2 = Integer.parseInt(""+cnpj.charAt(1));
			int val3 = Integer.parseInt(""+cnpj.charAt(2));
			int val4 = Integer.parseInt(""+cnpj.charAt(3));
			int val5 = Integer.parseInt(""+cnpj.charAt(4));
			int val6 = Integer.parseInt(""+cnpj.charAt(5));
			int val7 = Integer.parseInt(""+cnpj.charAt(6));
			
			int resultado = novesFora(val1,2);
			resultado += novesFora(val2,1);
			resultado += novesFora(val3,2);
			resultado += novesFora(val4,1);
			resultado += novesFora(val5,2);
			resultado += novesFora(val6,1);
			resultado += novesFora(val7,2);
			resultado = 30 - resultado;
			if(resultado < 0)
				resultado = (-1)*resultado;
			
			int verificador = Integer.parseInt(terceiro);
			
			if(resultado == verificador)
			{
				return true;
			}	
			else
			{
				System.out.println("O terceiro digito do cnpj deveria ser "+verificador+" por�m, � "+resultado);
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * M�todo noves fora.
	 * @param valor
	 * @param multiplicador
	 * @return N�mero abaixo de 9.
	 */
	private static int novesFora(int valor,int multiplicador)
	{
		int resultado = valor * multiplicador;
		if(resultado > 9)
			resultado -= 9;
		return resultado;
	}
	
	/**
	 * M�todo efetivo na verifica��o do cnpj.
	 * @param cnpj
	 * @return "true" ou "false" se o cnpj est�, ou n�o, correto.
	 */
	private static boolean cnpjOk(String cnpj)
	{
		if(verifica1cnpj(cnpj))
		{
			return verifica2cnpj(cnpj);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Primeira fase de verifica��o do cnpj.
	 * @param cnpj
	 * @return "true" ou "false" se est� correto ou n�o.
	 */
	private static boolean verifica1cnpj(String cnpj)
	{
		int val1 = Integer.parseInt(""+cnpj.charAt(0));
		int val2 = Integer.parseInt(""+cnpj.charAt(1));
		int val3 = Integer.parseInt(""+cnpj.charAt(2));
		int val4 = Integer.parseInt(""+cnpj.charAt(3));
		int val5 = Integer.parseInt(""+cnpj.charAt(4));
		int val6 = Integer.parseInt(""+cnpj.charAt(5));
		int val7 = Integer.parseInt(""+cnpj.charAt(6));
		int val8 = Integer.parseInt(""+cnpj.charAt(7));
		int val9 = Integer.parseInt(""+cnpj.charAt(8));
		int val10 = Integer.parseInt(""+cnpj.charAt(9));
		int val11 = Integer.parseInt(""+cnpj.charAt(10));
		int val12 = Integer.parseInt(""+cnpj.charAt(11));
		val1 *= 6;
		val2 *= 7;
		val3 *= 8;
		val4 *= 9;
		val5 *= 2;
		val6 *= 3;
		val7 *= 4;
		val8 *= 5;
		val9 *= 6;
		val10 *= 7;
		val11 *= 8;
		val12 *= 9;
		
		int resultado = val1 + val2 + val3 + val4 + val5 + val6 + val7 + val8 + val9 + val10 + val11 + val12;
		resultado = resultado % 11;
		if(resultado > 9)
			resultado = 0;
		
		int primeiro_digito = Integer.parseInt(""+cnpj.charAt(12));
		
		if(primeiro_digito == resultado)
			return true;
		else
			return false;
	}
	
	/**
	 * Segunda fase de verifica��o do cnpj.
	 * @param cnpj
	 * @return "true" ou "false" se est� correto ou n�o.
	 */
	private static boolean verifica2cnpj(String cnpj)
	{
		int val1 = Integer.parseInt(""+cnpj.charAt(0));
		int val2 = Integer.parseInt(""+cnpj.charAt(1));
		int val3 = Integer.parseInt(""+cnpj.charAt(2));
		int val4 = Integer.parseInt(""+cnpj.charAt(3));
		int val5 = Integer.parseInt(""+cnpj.charAt(4));
		int val6 = Integer.parseInt(""+cnpj.charAt(5));
		int val7 = Integer.parseInt(""+cnpj.charAt(6));
		int val8 = Integer.parseInt(""+cnpj.charAt(7));
		int val9 = Integer.parseInt(""+cnpj.charAt(8));
		int val10 = Integer.parseInt(""+cnpj.charAt(9));
		int val11 = Integer.parseInt(""+cnpj.charAt(10));
		int val12 = Integer.parseInt(""+cnpj.charAt(11));
		int val13 = Integer.parseInt(""+cnpj.charAt(12));
		val1 *= 5;
		val2 *= 6;
		val3 *= 7;
		val4 *= 8;
		val5 *= 9;
		val6 *= 2;
		val7 *= 3;
		val8 *= 4;
		val9 *= 5;
		val10 *= 6;
		val11 *= 7;
		val12 *= 8;
		val13 *= 9;
		
		int resultado = val1 + val2 + val3 + val4 + val5 + val6 + val7 + val8 + val9 + val10 + val11 + val12 +val13;
		resultado = resultado % 11;
		if(resultado > 9)
			resultado = 0;
		
		int segundo_digito = Integer.parseInt(""+cnpj.charAt(13));
		
		if(segundo_digito == resultado)
			return true;
		else
			return false;
	}

//----------------------------------------------------------------------CPF---------------------------------------------------------------------------------
	
	/**
	 * M�todo que informa se o cpf passado est� correto ou n�o.
	 * @param cpf = CPF a ser validado.
	 * @return "true" ou "false" se est� correto, ou n�o.
	 */
	private static boolean verificaCPF(String cpf)
	{
		cpf = cpf.replace(".", "");
		cpf = cpf.replace("-", "");
		cpf = cpf.trim();
		
		try
		{
			if(cpf.length() != 11)
			{
				return false;
			}
			else
			{
				if(soNumeros(cpf))
				{
					if(cpfOk(cpf))
						return true;
					else
					{
						return false;
					}
				}
				else
				{
					return false;
				}
			}
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	/**
	 * M�todo efetivo de avalia��o do CPF.
	 * @param cpf
	 * @return "true" ou "false" se estiver correto, ou n�o.
	 */
	private static boolean cpfOk(String cpf)
	{
		if(verifica1cpf(cpf))
		{
			return verifica2cpf(cpf);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Primeira fase de verifica��o do cpf.
	 * @param cpf
	 * @return "true" ou "false" se est� correto, ou n�o.
	 */
	private static boolean verifica1cpf(String cpf)
	{
		int val1 = Integer.parseInt(""+cpf.charAt(0));
		int val2 = Integer.parseInt(""+cpf.charAt(1));
		int val3 = Integer.parseInt(""+cpf.charAt(2));
		int val4 = Integer.parseInt(""+cpf.charAt(3));
		int val5 = Integer.parseInt(""+cpf.charAt(4));
		int val6 = Integer.parseInt(""+cpf.charAt(5));
		int val7 = Integer.parseInt(""+cpf.charAt(6));
		int val8 = Integer.parseInt(""+cpf.charAt(7));
		int val9 = Integer.parseInt(""+cpf.charAt(8));
		
		val1 *= 1;
		val2 *= 2;
		val3 *= 3;
		val4 *= 4;
		val5 *= 5;
		val6 *= 6;
		val7 *= 7;
		val8 *= 8;
		val9 *= 9;
		
		int result = (val1 + val2 + val3 + val4 + val5 + val6 + val7 + val8 + val9) % 11;
		if(result > 9)
			result = 0;
		
		int primeiro_digito = Integer.parseInt(""+cpf.charAt(9));
		
		if(primeiro_digito == result)
			return true;
		else
			return false;
		
	}
	
	/**
	 * Segunda fase de verifica��o do cpf.
	 * @param cpf
	 * @return "true" ou "false" se est� correto, ou n�o.
	 */
	private static boolean verifica2cpf(String cpf)
	{
		int val1 = Integer.parseInt(""+cpf.charAt(1));
		int val2 = Integer.parseInt(""+cpf.charAt(2));
		int val3 = Integer.parseInt(""+cpf.charAt(3));
		int val4 = Integer.parseInt(""+cpf.charAt(4));
		int val5 = Integer.parseInt(""+cpf.charAt(5));
		int val6 = Integer.parseInt(""+cpf.charAt(6));
		int val7 = Integer.parseInt(""+cpf.charAt(7));
		int val8 = Integer.parseInt(""+cpf.charAt(8));
		int val9 = Integer.parseInt(""+cpf.charAt(9));
		
		val1 *= 1;
		val2 *= 2;
		val3 *= 3;
		val4 *= 4;
		val5 *= 5;
		val6 *= 6;
		val7 *= 7;
		val8 *= 8;
		val9 *= 9;
		
		int result = (val1 + val2 + val3 + val4 + val5 + val6 + val7 + val8 + val9) % 11;
		if(result > 9)
			result = 0;
		
		int segundo_digito = Integer.parseInt(""+cpf.charAt(10));
		
		if(segundo_digito == result)
			return true;
		else
			return false;
		
	}
	
}
