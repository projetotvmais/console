package br.com.douglasfernandes.console.dao;

import java.util.List;

import br.com.douglasfernandes.console.model.Token;

/**
 * Como realizar operacoes com tokens
 * @author douglas.f.filho
 *
 */
public interface TokenDao {
	public String cadastrar(Token token);
	public String remover(long id);
	public List<Token> listar();
	public Token validar(String nome);
}
