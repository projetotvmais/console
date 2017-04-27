package br.com.douglasfernandes.console.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Wrapper para logar mensagens do app.
 * @author Compliance Software (by Douglas Fernandes)
 *
 */
public class Logs{
	
	/**
	 * Loga uma informação do sistema.
	 * @param mensagem
	 */
	public static void info(String mensagem){
		Logger logger = Logger.getLogger("compinfo");
		logger.log(Level.INFO, mensagem);
	}
	
	/**
	 * Loga uma informação do sistema.
	 * @param mensagem
	 */
	public static void warn(String mensagem){
		Logger logger = Logger.getLogger("compwarn");
		logger.log(Level.WARNING, mensagem);
	}
	
	/**
	 * Loga uma informação do sistema.
	 * @param mensagem
	 */
	public static void debug(String mensagem){
		Logger logger = Logger.getLogger("compdebug");
		logger.log(Level.CONFIG, mensagem);
	}
}
