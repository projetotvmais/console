/**
 * 
 */
package br.com.douglasfernandes.console.controller.utils;

/**
 * @author Douglas Fernandes <douglasf.filho@gmail.com>
 *
 */
public class Mensagem 
{
	private static final String DIV_SUCCESS = "<div id=\"alert-closeable\" class=\"alert alert-success\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>";
	private static final String DIV_DANGER = "<div id=\"alert-closeable\" class=\"alert alert-danger\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>";
	private static final String DIV_INFO = "<div id=\"alert-closeable\" class=\"alert alert-info\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>";
	private static final String DIV_WARNING = "<div id=\"alert-closeable\" class=\"alert alert-warning\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>";
	private static final String END_DIV = "</div>";
	
	/**
	 * Retorna uma mensagem formatada de sucesso(fundo verde).
	 * @param message
	 * @return
	 */
	public static String getSuccess(String message){
		return DIV_SUCCESS + message + END_DIV;
	}
	
	/**
	 * Retorna uma mensagem formatada de informa��o(fundo azul).
	 * @param message
	 * @return
	 */
	public static String getInfo(String message){
		return DIV_INFO + message + END_DIV;
	}
	
	/**
	 * Retorna uma mensagem formatada de erro(fundo vermelho).
	 * @param message
	 * @return
	 */
	public static String getDanger(String message){
		return DIV_DANGER + message + END_DIV;
	}
	
	/**
	 * Retorna uma mensagem formatada de aviso(fundo laranja).
	 * @param message
	 * @return
	 */
	public static String getWarning(String message){
		return DIV_WARNING + message + END_DIV;
	}
	
	/**
	 * Verifica se a mensagem passada � de sucesso.
	 * @param message
	 * @return
	 */
	public static boolean isSuccess(String message){
		return message.contains("alert alert-success");
	}
	
	/**
	 * Verifica se a mensagem passada � de erro.
	 * @param message
	 * @return
	 */
	public static boolean isDanger(String message){
		return message.contains("alert alert-danger");
	}
	
	/**
	 * Verifica se a mensagem passada � de informa��o.
	 * @param message
	 * @return
	 */
	public static boolean isInfo(String message){
		return message.contains("alert alert-info");
	}
	
	/**
	 * Verifica se a mensagem passada � de aviso.
	 * @param message
	 * @return
	 */
	public static boolean isWarnning(String message){
		return message.contains("alert alert-warning");
	}
}
