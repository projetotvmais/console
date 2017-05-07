package br.com.douglasfernandes.console.dao;

import java.util.List;

import br.com.douglasfernandes.console.model.Classificacao;

/**
 * Interface de comunicação com a entidade de classificacoes no banco de dados.
 * @author douglas.f.filho
 *
 */
public interface ClassificacaoDao {
	public String cadastrar(Classificacao classificacao);
	public String atualizar(Classificacao classificacao);
	public String remover(long id);
	public Classificacao pegarClassificacao(long id);
	public List<Classificacao> listar();
	public void primeiroAcesso();
}
