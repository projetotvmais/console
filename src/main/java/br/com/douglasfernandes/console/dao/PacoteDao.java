package br.com.douglasfernandes.console.dao;

import java.util.List;

import br.com.douglasfernandes.console.model.Pacote;

/**
 * Interface de acesso a banco de dados para realizar as operações com a tabela de pacotes.
 * @author douglas.f.filho
 *
 */
public interface PacoteDao {
	public String cadastrar(Pacote pacote);
	public String atualizar(Pacote pacote);
	public String remover(long id);
	public byte[] pegarLogoDoPacote(long id);
	public Pacote pegarPacotePorId(long id);
	public List<Pacote> listar(String nome);
}
