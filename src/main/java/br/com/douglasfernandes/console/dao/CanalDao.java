package br.com.douglasfernandes.console.dao;

import java.util.List;

import br.com.douglasfernandes.console.model.Canal;

/**
 * Interface de manipulação de dados relacionados aos canais.
 * @author douglas.f.filho
 *
 */
public interface CanalDao {
	public String cadastrar(Canal canal);
	public String atualizar(Canal canal);
	public String remover(Canal canal);
	public List<Canal> listarPorClassificacao(String classificaco);
	public List<Canal> listarPorNome(String nome);
	public List<Canal> listarPorStatus(boolean funcionando);
}
